package com.tmt.kontroll.web.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.tmt.kontroll.business.preparation.BusinessEntityScanner;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingServiceScanner;
import com.tmt.kontroll.security.persistence.entities.Authority;
import com.tmt.kontroll.security.persistence.entities.UserAccount;
import com.tmt.kontroll.security.persistence.services.AuthorityDaoService;
import com.tmt.kontroll.security.persistence.services.UserAccountDaoService;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfiguratorScanner;
import com.tmt.kontroll.ui.page.preparation.PagePreparator;
import com.tmt.kontroll.ui.page.preparation.PageScanner;
import com.tmt.kontroll.ui.page.preparation.PageSegmentScanner;

/**
 * <p>
 * Abstract base class for the application's context started handler. It implements Spring's
 * {@link ApplicationListener} with a {@link ContextRefreshedEvent}. When called, it performs
 * the following necessary steps to get the Kontroll app working properly:
 * </p>
 * <p>
 * 	<ul>
 * 		<li>prepare the application's package properties for classpath scanning according to implementation of this class</li>
 * 		<li>prepare the Kontroll context by class path scanning and setting up all necessary framework components as defined</li>
 * 		<li>prepare the database to assure that certain values are present for the framework to work properly</li>
 * 		<li>prepare the pages with all their segments, events, contents and all that</li>
 * 	</ul>
 * </p>
 * <p>
 * In the end, the application is ready to rumble.
 * </p>
 *
 * @author SergDerbst
 *
 */
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

	@Autowired
	AuthorityDaoService							authorityService;

	@Autowired
	UserAccountDaoService						userService;

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent e) {
		if (this.applicationContext.getId().equals(e.getApplicationContext().getId())) {
			this.prepareAppPackageProperties();
			this.prepareKontrollContext();
			this.prepareDataBase();
			this.pagePreparator.prepare();
		}
	}

	protected abstract void prepareAppPackageProperties();

	private void prepareKontrollContext() {
		this.businessEntityScanner.scan();
		this.requestHandlingServiceScanner.scan();
		this.pageSegmentConfigurationScanner.scan();
		this.pageSegmentScanner.scan();
		this.pageScanner.scan();
	}

	private void prepareDataBase() {
		UserAccount kontroll = this.userService.findByName("Kontroll");
		if (kontroll == null) {
			kontroll = new UserAccount();
			kontroll.setEnabled(true);
			kontroll.setLocale(Locale.US);
			kontroll.setName("Kontroll");
			kontroll.setPassword(RandomStringUtils.random(25));
			kontroll.setAuthorities(this.prepareAuthority());
			this.userService.create(kontroll);
		}
	}

	private List<Authority> prepareAuthority() {
		final List<Authority> authorities = new ArrayList<>();
		Authority authority = this.authorityService.findByName("System");
		if (authority == null) {
			authority = new Authority();
			authority.setName("System");
			this.authorityService.create(authority);
		}
		authorities.add(authority);
		return authorities;
	}
}
