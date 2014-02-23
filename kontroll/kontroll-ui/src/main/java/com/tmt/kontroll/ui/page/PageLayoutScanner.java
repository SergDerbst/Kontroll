package com.tmt.kontroll.ui.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.tmt.kontroll.annotations.Layout;
import com.tmt.kontroll.commons.exceptions.ScanFailedException;
import com.tmt.kontroll.commons.utils.AnnotationAndAssignableTypeCandidateScanner;
import com.tmt.kontroll.content.config.ContentProperties;
import com.tmt.kontroll.ui.config.UiProperties;
import com.tmt.kontroll.ui.page.layout.PageLayout;
import com.tmt.kontroll.ui.page.layout.PageLayoutBody;
import com.tmt.kontroll.ui.page.layout.PageLayoutFooter;
import com.tmt.kontroll.ui.page.layout.PageLayoutHeader;

/**
 * The page layout scanner scans the base packages defined in the {@link ContentProperties} bean for 
 * classes of type {@link PageLayout} that are annotated with {@link Layout}. It then
 * iterates over all URL patterns declared in the annotation, so that for each pattern a page layout 
 * is created.
 * 
 * @author Sergio Weigel
 *
 */
@Component
public class PageLayoutScanner {

	@Autowired
	UiProperties contentProperties;

	@Autowired
	PageLayout pageLayout;
	
	@Autowired
	PageContentManager pageContentManager;

	@Autowired
	AnnotationAndAssignableTypeCandidateScanner candidateScanner;

	public void scan() {
		try {
			this.scanHeaders();
			this.scanBodies();
			this.scanFooters();
			this.pageContentManager.prepareContent();
		} catch (Exception e) {
			throw new ScanFailedException(e);
		}
	}

	private void scanHeaders() throws Exception {
		for (final BeanDefinition beanDefinition : this.candidateScanner.scan(Layout.class, PageLayoutHeader.class, this.contentProperties.getLayoutBasePackages())) {
			@SuppressWarnings("unchecked")
			final Class<? extends PageLayoutHeader> headerClass = (Class<? extends PageLayoutHeader>) ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader());
			for (final String pattern : headerClass.getAnnotation(Layout.class).patterns()) {
				this.pageLayout.addHeader(pattern, headerClass.newInstance());
			}
		}
	}

	private void scanBodies() throws Exception {
		for (final BeanDefinition beanDefinition : this.candidateScanner.scan(Layout.class, PageLayoutBody.class, this.contentProperties.getLayoutBasePackages())) {
			@SuppressWarnings("unchecked")
			final Class<? extends PageLayoutBody> bodyClass = (Class<? extends PageLayoutBody>) ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader());
			for (final String pattern : bodyClass.getAnnotation(Layout.class).patterns()) {
				this.pageLayout.addBody(pattern, bodyClass.newInstance());
			}
		}
	}

	private void scanFooters() throws Exception {
		for (final BeanDefinition beanDefinition : this.candidateScanner.scan(Layout.class, PageLayoutFooter.class, this.contentProperties.getLayoutBasePackages())) {
			@SuppressWarnings("unchecked")
			final Class<? extends PageLayoutFooter> footerClass = (Class<? extends PageLayoutFooter>) ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader());
			for (final String pattern : footerClass.getAnnotation(Layout.class).patterns()) {
				this.pageLayout.addFooter(pattern, footerClass.newInstance());
			}
		}
	}
}
