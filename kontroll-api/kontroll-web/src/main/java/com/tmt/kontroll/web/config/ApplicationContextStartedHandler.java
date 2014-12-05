package com.tmt.kontroll.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.tmt.kontroll.business.preparation.BusinessEntityScanner;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingServiceScanner;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfiguratorScanner;
import com.tmt.kontroll.ui.page.preparation.PagePreparator;
import com.tmt.kontroll.ui.page.preparation.PageScanner;
import com.tmt.kontroll.ui.page.preparation.PageSegmentScanner;

public abstract class ApplicationContextStartedHandler implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	BusinessEntityScanner						businessEntityScanner;

	@Autowired
	PageSegmentConfiguratorScanner	pageSegmentConfigurationScanner;

	@Autowired
	PageSegmentScanner							pageSegmentScanner;

	@Autowired
	PageScanner											pageScanner;

	@Autowired
	RequestHandlingServiceScanner		requestHandlingServiceScanner;

	@Autowired
	ApplicationContext							applicationContext;

	@Autowired
	PagePreparator									pagePreparator;

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent e) {
		if (this.applicationContext.getId().equals(e.getApplicationContext().getId())) {
			this.preparePackageProperties();
			this.prepareKontrollContext();
			this.pagePreparator.prepare();
		}
	}

	protected abstract void preparePackageProperties();

	private void prepareKontrollContext() {
		this.businessEntityScanner.scan();
		this.requestHandlingServiceScanner.scan();
		this.pageSegmentConfigurationScanner.scan();
		this.pageSegmentScanner.scan();
		this.pageScanner.scan();
	}
}
