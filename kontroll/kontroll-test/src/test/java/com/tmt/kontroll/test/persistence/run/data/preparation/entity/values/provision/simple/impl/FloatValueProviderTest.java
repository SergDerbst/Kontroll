package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class FloatValueProviderTest extends ValueProviderTest<Float> {

	public FloatValueProviderTest() throws Exception {
		super(new FloatValueProvider(ValueProvisionHandler.newInstance()),
		      new Float(0.0),
		      new Float(1.0),
		      Float.class.getSimpleName(),
		      Dummy.class,
		      Float.class);
	}
}
