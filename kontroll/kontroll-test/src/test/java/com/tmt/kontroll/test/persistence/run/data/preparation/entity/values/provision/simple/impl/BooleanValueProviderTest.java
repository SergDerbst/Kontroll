package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class BooleanValueProviderTest extends ValueProviderTest<Boolean> {

	public BooleanValueProviderTest() throws Exception {
		super(new BooleanValueProvider(ValueProvisionHandler.newInstance()),
		      false,
		      true,
		      Boolean.class.getSimpleName(),
		      Dummy.class,
		      Boolean.class);
	}
}
