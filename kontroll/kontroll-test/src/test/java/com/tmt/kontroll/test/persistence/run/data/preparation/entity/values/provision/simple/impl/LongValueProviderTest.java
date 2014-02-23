package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class LongValueProviderTest extends ValueProviderTest<Long> {

	public LongValueProviderTest() throws Exception {
		super(new LongValueProvider(ValueProvisionHandler.newInstance()),
		      new Long(0),
		      new Long(1),
		      Long.class.getSimpleName(),
		      Dummy.class,
		      Long.class);
	}
}
