package com.tmt.kontroll.web.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 * Implementation of {@link ApplicationContextInitializer} that will add property sources to
 * the environment according to the purpose given as system property.
 *
 * @author SergDerbst
 *
 */
public class KontrollContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static Logger				Log			= LoggerFactory.getLogger(KontrollContextInitializer.class);
	private static final String	Purpose	= "purpose";

	@Override
	public void initialize(final ConfigurableApplicationContext applicationContext) {
		final ConfigurableEnvironment environment = applicationContext.getEnvironment();
		this.initializeEnvironmentProperties(environment);
	}

	private void initializeEnvironmentProperties(final ConfigurableEnvironment environment) {
		final String purpose = System.getenv(Purpose);
		if (purpose == null || purpose.isEmpty()) {
			throw EnvironmentInitializationException.prepare(this.createEnvironmentVariablesMap(Purpose, purpose));
		}
		try {
			environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:/jpaConfig/" + purpose + "/jpaConfig.properties"));
		} catch (final IOException e) {
			Log.info("Could not find jpaConfig.properties in classpath for purpose " + purpose + " - try loading default instead.");
		} finally {
			try {
				environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:/jpaConfig/default/jpaConfig.properties"));
			} catch (final IOException e) {
				throw EnvironmentInitializationException.prepare(e, this.createEnvironmentVariablesMap(Purpose, purpose));
			}
		}
	}

	@SuppressWarnings("serial")
	private Map<String, String> createEnvironmentVariablesMap(final String key, final String value) {
		return new HashMap<String, String>() {
			{
				this.put(key, value);
			}
		};
	}
}
