package com.tmt.kontroll.ui.page.configuration.impl.context;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.PageHolder;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.CssHandler;
import com.tmt.kontroll.ui.page.segments.PageSegment;
import com.tmt.kontroll.ui.page.segments.PageSegmentHolder;

/**
 * <p>
 * Configures {@link PageSegment}s annotated with {@link PageConfig}.
 * </p>
 * <p>
 * During preparation phase, the configurator iterates through all {@link PageContext} annotation
 * present in {@link PageConfig#contexts} and adds both segments and the according page to the
 * {@link PageHolder} and {@link PageSegmentHolder}.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class PageConfigConfigurator extends PageSegmentConfigurator {

	@Autowired
	CssHandler				cssHandler;

	@Autowired
	PageHolder				pageHolder;

	@Autowired
	PageSegmentHolder	pageSegmentHolder;

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return PageConfig.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		final PageConfig pageConfig = segment.getClass().getAnnotation(PageConfig.class);
		for (final PageContext pageContext : pageConfig.contexts()) {
			this.handleSegmentContext(segment, pageContext);
		}
	}

	private void handleSegmentContext(final PageSegment segment, final PageContext pageContext) {
		final String[] scopePath = pageContext.scope().split("\\.");
		segment.setParentScope(this.generateParentScopeName(scopePath));
		segment.setScope(scopePath[scopePath.length - 1]);
		segment.setOrdinal(pageContext.ordinal());
		segment.getRequestPatterns().add(pageContext.pattern());
		this.cssHandler.handle(segment);
		this.pageSegmentHolder.addPageSegment(pageContext.scope(), segment);
	}

	private String generateParentScopeName(final String[] scopePath) {
		final StringBuilder s = new StringBuilder();
		for (int i = 0; i < scopePath.length - 1; i++) {
			s.append(scopePath[i]);
			if (i < scopePath.length - 2) {
				s.append(".");
			}
		}
		return s.toString();
	}
}
