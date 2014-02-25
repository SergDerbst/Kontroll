package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.IdValueProviderTest;

public class IntegerIdValueProviderTest extends IdValueProviderTest<Integer> {

	public IntegerIdValueProviderTest() throws Exception {
		super(new IntegerIdValueProvider(ValueProvisionHandler.newInstance(), Dummy.class),
		      1,
		      2,
		      Dummy.class,
		      Integer.class);
	}
}
