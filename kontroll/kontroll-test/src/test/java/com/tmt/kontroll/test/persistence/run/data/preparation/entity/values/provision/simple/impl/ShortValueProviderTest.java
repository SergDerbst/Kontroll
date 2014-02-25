package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProviderTest;

public class ShortValueProviderTest extends SimpleValueProviderTest<Short> {

	public ShortValueProviderTest() throws Exception {
		super(new ShortValueProvider(ValueProvisionHandler.newInstance()),
		      (short) 0,
		      (short) 1,
		      Dummy.class,
		      Short.class);
	}
}
