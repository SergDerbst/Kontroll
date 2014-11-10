package com.tmt.kontroll.ui.page.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.impl.context.PageConfigConfigurator;

/**
 * Responsibility chain handler for configuring {@link PageSegment}s during preparation phase.
 *
 * @author SergDerbst
 *
 */
@Component
public class PageSegmentConfigurationHandler {

	@Autowired
	PageConfigConfigurator					firstConfigurator;
	private PageSegmentConfigurator	lastConfigurator;

	@PostConstruct
	public void initialize() {
		this.lastConfigurator = this.firstConfigurator;
	}

	public void configure(final PageSegment segment) {
		this.firstConfigurator.configure(segment);
		segment.setConfigured(true);
	}

	public void addConfigurator(final PageSegmentConfigurator configurator) {
		if (configurator != this.firstConfigurator) {
			this.lastConfigurator.setNextConfigurator(configurator);
			this.lastConfigurator = configurator;
		}
	}
}
