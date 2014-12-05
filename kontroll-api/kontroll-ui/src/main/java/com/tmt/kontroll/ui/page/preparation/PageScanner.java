package com.tmt.kontroll.ui.page.preparation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.tmt.kontroll.commons.exceptions.ScanFailedException;
import com.tmt.kontroll.commons.utils.scanning.AnnotationAndAssignableTypeCandidateScanner;
import com.tmt.kontroll.content.config.ContentProperties;
import com.tmt.kontroll.ui.config.UiProperties;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurationHandler;
import com.tmt.kontroll.ui.page.configuration.annotations.context.Page;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * <p>
 * The page layout scanner scans the base packages defined in the {@link ContentProperties} bean for
 * classes of type {@link PageSegment} that are annotated with {@link Page}. It then calls the
 * {@link PageSegmentConfigurationHandler} for each found page segment in order to configure them properly.
 * </p>
 *
 * @author Sergio Weigel
 *
 */
@Component
public class PageScanner {

	@Autowired
	UiProperties																uiProperties;

	@Autowired
	AnnotationAndAssignableTypeCandidateScanner	candidateScanner;

	@Autowired
	PageSegmentConfigurationHandler							configurationHandler;

	public void scan() {
		try {
			final Set<BeanDefinition> candidates = this.candidateScanner.scan(Page.class, PageSegment.class, this.uiProperties.basePackages());
			for (final BeanDefinition beanDefinition : candidates) {
				@SuppressWarnings("unchecked")
				final Class<? extends PageSegment> segmentClass = (Class<? extends PageSegment>) ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader());
				this.configurationHandler.configure(segmentClass.newInstance());
			}
		} catch (final Exception e) {
			throw new ScanFailedException(e);
		}
	}
}
