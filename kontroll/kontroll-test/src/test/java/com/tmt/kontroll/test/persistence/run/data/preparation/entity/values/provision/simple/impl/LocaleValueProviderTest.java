package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import java.util.Locale;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class LocaleValueProviderTest extends ValueProviderTest<Locale> {

	public LocaleValueProviderTest() throws Exception {
		super(new LocaleValueProvider(ValueProvisionHandler.newInstance()),
		      Locale.GERMAN,
		      Locale.GERMANY,
		      Locale.class.getSimpleName(),
		      Dummy.class,
		      Locale.class);
	}
}
