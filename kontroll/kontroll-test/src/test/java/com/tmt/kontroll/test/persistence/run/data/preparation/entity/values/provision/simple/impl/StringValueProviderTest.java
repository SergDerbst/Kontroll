package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;


import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class StringValueProviderTest extends ValueProviderTest<String> {

	public StringValueProviderTest() throws Exception {
		super(new StringValueProvider(ValueProvisionHandler.newInstance()),
		      "0",
		      "1",
		      String.class.getSimpleName(),
		      Dummy.class,
		      String.class);
	}
}
