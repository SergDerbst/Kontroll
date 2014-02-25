package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import java.util.Locale;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProviderTest;

public class LocaleValueProviderTest extends SimpleValueProviderTest<Locale> {

	public LocaleValueProviderTest() throws Exception {
		super(new LocaleValueProvider(ValueProvisionHandler.newInstance()),
		      Locale.GERMAN,
		      Locale.GERMANY,
		      Dummy.class,
		      Locale.class);
	}
}
