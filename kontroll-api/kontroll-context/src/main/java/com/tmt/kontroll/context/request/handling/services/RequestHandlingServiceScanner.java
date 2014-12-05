package com.tmt.kontroll.context.request.handling.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.support.RequestContext;

import com.tmt.kontroll.commons.exceptions.ScanFailedException;
import com.tmt.kontroll.commons.utils.scanning.AnnotationAndAssignableTypeCandidateScanner;
import com.tmt.kontroll.context.annotations.RequestHandler;
import com.tmt.kontroll.context.config.ContextProperties;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.context.request.RequestContextItem;
import com.tmt.kontroll.context.request.data.RequestContextDtoPathScanner;

/**
 * The request context scanner scans the base packages defined in the {@link ContentProperties} bean for
 * classes of type {@link RequestHandler} that are annotated with {@link RequestContext}. It then
 * iterates over all URL patterns declared in the annotation, so that for each pattern a request context
 * is created, when it does not exist yet, consisting of services and all according DTO paths available
 * for each service.
 *
 * @author Sergio Weigel
 *
 */
@Component
public class RequestHandlingServiceScanner {

	@Autowired
	ContextProperties														contextProperties;

	@Autowired
	GlobalContext																globalContext;

	@Autowired
	RequestContextDtoPathScanner								dtoPathScanner;

	@Autowired
	AnnotationAndAssignableTypeCandidateScanner	candidateScanner;

	@Autowired
	ApplicationContext													applicationContext;

	public void scan() {
		try {
			for (final BeanDefinition beanDefinition : this.candidateScanner.scan(RequestHandler.class, RequestHandlingService.class, this.contextProperties.contextServiceBasePackages())) {
				@SuppressWarnings("unchecked")
				final Class<? extends RequestHandlingService> serviceClass = (Class<? extends RequestHandlingService>) ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader());
				for (final String pattern : serviceClass.getAnnotation(RequestHandler.class).patterns()) {
					final RequestHandlingService service = this.applicationContext.getBean(serviceClass);
					this.globalContext.requestContextHolder().addRequestContextItem(pattern, new RequestContextItem(service, this.dtoPathScanner.scan(service)));
				}
			}
		} catch (final ScanFailedException e) {
			throw e;
		} catch (final Exception e) {
			throw new ScanFailedException(e);
		}
	}
}
