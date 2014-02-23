package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class IntegerIdValueProviderTest extends ValueProviderTest<Integer> {

	public IntegerIdValueProviderTest() throws Exception {
		super(new IntegerIdValueProvider(ValueProvisionHandler.newInstance(), Dummy.class),
		      1,
		      2,
		      Integer.class.getSimpleName() + "Id",
		      Dummy.class,
		      Integer.class);
	}
}
