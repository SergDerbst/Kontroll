package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class DoubleValueProviderTest extends ValueProviderTest<Double> {

	public DoubleValueProviderTest() throws Exception {
		super(new DoubleValueProvider(ValueProvisionHandler.newInstance()),
		      new Double(0.0),
		      new Double(1.0),
		      Double.class.getSimpleName(),
		      Dummy.class,
		      Double.class);
	}
}
