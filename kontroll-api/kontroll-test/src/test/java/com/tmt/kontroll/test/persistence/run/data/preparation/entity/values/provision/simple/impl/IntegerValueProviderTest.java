package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProviderTest;

public class IntegerValueProviderTest extends SimpleValueProviderTest<Integer> {

	public IntegerValueProviderTest() throws Exception {
		super(new IntegerValueProvider(ValueProvisionHandler.newInstance()),
		      0,
		      1,
		      Dummy.class,
		      Integer.class);
	}
}
