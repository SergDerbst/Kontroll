package com.tmt.kontroll.ui.page.configuration.impl.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.Page;
import com.tmt.kontroll.ui.page.PageHolder;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.PageSegmentHolder;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;

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
	PageHolder				pageHolder;

	@Autowired
	PageSegmentHolder	pageSegmentHolder;

	@Override
	protected boolean isResponsible(final PageSegment segment) {
		return segment.getClass().isAnnotationPresent(PageConfig.class);
	}

	@Override
	protected void doConfiguration(final PageSegment segment) {
		final PageConfig pageConfig = segment.getClass().getAnnotation(PageConfig.class);
		for (final PageContext pageContext : pageConfig.contexts()) {
			this.handlePage(pageContext);
			this.handleSegmentContext(segment, pageContext);
		}
	}

	private void handlePage(final PageContext pageContext) {
		final Page page = new Page();
		page.setScope("page");
		this.pageHolder.addPage(pageContext.pattern(), page);
	}

	private void handleSegmentContext(final PageSegment segment, final PageContext pageContext) {
		final String[] scopePath = pageContext.scope().split("\\.");
		segment.setParentScope(this.generateParentScopeName(scopePath));
		segment.setScope(scopePath[scopePath.length - 1]);
		segment.setOrdinal(pageContext.ordinal());
		segment.getRequestPatterns().add(pageContext.pattern());
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
