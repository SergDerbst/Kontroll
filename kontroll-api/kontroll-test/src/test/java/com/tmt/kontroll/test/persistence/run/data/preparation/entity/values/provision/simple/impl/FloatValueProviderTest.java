package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProviderTest;

public class FloatValueProviderTest extends SimpleValueProviderTest<Float> {

	public FloatValueProviderTest() throws Exception {
		super(new FloatValueProvider(ValueProvisionHandler.newInstance()),
		      new Float(0.0),
		      new Float(1.0),
		      Dummy.class,
		      Float.class);
	}
}
