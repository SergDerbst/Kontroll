package com.tmt.kontroll.ui.page.configuration;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.PageSegment;

/**
 * Responsibility chain element for configuring {@link PageSegment}s.
 *
 * @author SergDerbst
 *
 */
@Component
public abstract class PageSegmentConfigurator {

	private PageSegmentConfigurator	nextConfigurator;

	protected abstract boolean isResponsible(final PageSegment segment);

	protected abstract void doConfiguration(final PageSegment segment);

	public void configure(final PageSegment segment) {
		if (this.isResponsible(segment)) {
			this.doConfiguration(segment);
		}
		if (this.nextConfigurator != null) {
			this.nextConfigurator.configure(segment);
		}
	}

	public void setNextConfigurator(final PageSegmentConfigurator nextConfigurator) {
		this.nextConfigurator = nextConfigurator;
	}
}
