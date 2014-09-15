package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;


import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProviderTest;

public class StringValueProviderTest extends SimpleValueProviderTest<String> {

	public StringValueProviderTest() throws Exception {
		super(new StringValueProvider(ValueProvisionHandler.newInstance()),
		      "0",
		      "1",
		      Dummy.class,
		      String.class);
	}
}
