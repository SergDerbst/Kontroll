package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProviderTest;

public class LongValueProviderTest extends SimpleValueProviderTest<Long> {

	public LongValueProviderTest() throws Exception {
		super(new LongValueProvider(ValueProvisionHandler.newInstance()),
		      new Long(0),
		      new Long(1),
		      Dummy.class,
		      Long.class);
	}
}
