package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class ShortValueProviderTest extends ValueProviderTest<Short> {

	public ShortValueProviderTest() throws Exception {
		super(new ShortValueProvider(ValueProvisionHandler.newInstance()),
		      (short) 0,
		      (short) 1,
		      Short.class.getSimpleName(),
		      Dummy.class,
		      Short.class);
	}
}
