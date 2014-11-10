package com.tmt.kontroll.context.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.tmt.kontroll.annotations.RequestContext;
import com.tmt.kontroll.commons.exceptions.ScanFailedException;
import com.tmt.kontroll.commons.utils.scanning.AnnotationAndAssignableTypeCandidateScanner;
import com.tmt.kontroll.context.config.ContextProperties;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.context.global.GlobalContextDto;

/**
 * The request context scanner scans the base packages defined in the {@link ContentProperties} bean for
 * classes of type {@link RequestContextService} that are annotated with {@link RequestContext}. It then
 * iterates over all URL patterns declared in the annotation, so that for each pattern a request context
 * is created, when it does not exist yet, consisting of services and all according DTO paths available
 * for each service.
 *
 * @author Sergio Weigel
 *
 */
@Component
public class RequestContextScanner {

	@Autowired
	ContextProperties														contextProperties;

	@Autowired
	GlobalContext																globalContext;

	@Autowired
	RequestContextDtoPathScanner								dtoPathScanner;

	@Autowired
	AnnotationAndAssignableTypeCandidateScanner	candidateScanner;

	public void scan() {
		try {
			for (final BeanDefinition beanDefinition : this.candidateScanner.scan(RequestContext.class, RequestContextService.class, this.contextProperties.contextServiceBasePackages())) {
				@SuppressWarnings("unchecked")
				final Class<? extends RequestContextService<? extends RequestContextDto, ? extends GlobalContextDto>> serviceClass = (Class<? extends RequestContextService<? extends RequestContextDto, ? extends GlobalContextDto>>) ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader());
				final RequestContextService<? extends RequestContextDto, ? extends GlobalContextDto> service = serviceClass.newInstance();
				for (final String pattern : serviceClass.getAnnotation(RequestContext.class).patterns()) {
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
