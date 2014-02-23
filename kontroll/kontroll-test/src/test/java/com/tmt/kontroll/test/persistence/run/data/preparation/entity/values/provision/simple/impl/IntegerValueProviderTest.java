package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class IntegerValueProviderTest extends ValueProviderTest<Integer> {

	public IntegerValueProviderTest() throws Exception {
		super(new IntegerValueProvider(ValueProvisionHandler.newInstance()),
		      0,
		      1,
		      Integer.class.getSimpleName(),
		      Dummy.class,
		      Integer.class);
	}
}
