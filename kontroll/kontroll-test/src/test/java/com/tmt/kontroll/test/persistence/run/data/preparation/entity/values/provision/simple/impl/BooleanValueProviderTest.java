package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProviderTest;

public class BooleanValueProviderTest extends SimpleValueProviderTest<Boolean> {

	public BooleanValueProviderTest() throws Exception {
		super(new BooleanValueProvider(ValueProvisionHandler.newInstance()),
		      false,
		      true,
		      Dummy.class,
		      Boolean.class);
	}
}
