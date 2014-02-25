package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProviderTest;

public class DoubleValueProviderTest extends SimpleValueProviderTest<Double> {

	public DoubleValueProviderTest() throws Exception {
		super(new DoubleValueProvider(ValueProvisionHandler.newInstance()),
		      new Double(0.0),
		      new Double(1.0),
		      Dummy.class,
		      Double.class);
	}
}
