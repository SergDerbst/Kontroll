package com.tmt.kontroll.ui.page.preparation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.tmt.kontroll.commons.exceptions.ScanFailedException;
import com.tmt.kontroll.commons.utils.AnnotationAndAssignableTypeCandidateScanner;
import com.tmt.kontroll.content.config.ContentProperties;
import com.tmt.kontroll.ui.config.UiProperties;
import com.tmt.kontroll.ui.page.layout.PageSegment;
import com.tmt.kontroll.ui.page.layout.PageSegmentHolder;
import com.tmt.kontroll.ui.page.management.annotations.PageConfig;

/**
 * <p>
 * The page layout scanner scans the base packages defined in the {@link ContentProperties} bean for
 * classes of type {@link PageSegment} that are annotated with {@link PageConfig}. It then adds the
 * annotated page segments to the {@link PageSegmentHolder}, where they will live during the application's
 * life cycle.
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
	PageSegmentHolder														pageSegmentHolder;

	@Autowired
	PagePreparator															pagePreparator;

	public void scan() {
		try {
			for (final BeanDefinition beanDefinition : this.candidateScanner.scan(PageConfig.class, PageSegment.class, this.uiProperties.layoutBasePackages())) {
				@SuppressWarnings("unchecked")
				final Class<? extends PageSegment> segmentClass = (Class<? extends PageSegment>) ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader());
				this.pageSegmentHolder.addPageSegment(segmentClass.newInstance());
			}
			this.pagePreparator.prepare();
		} catch (final Exception e) {
			throw new ScanFailedException(e);
		}
	}
}
