package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.IdValueProviderTest;

public class LongIdValueProviderTest extends IdValueProviderTest<Long> {

	public LongIdValueProviderTest() throws Exception {
		super(new LongIdValueProvider(ValueProvisionHandler.newInstance(), Dummy.class),
		      (long) 1,
		      (long) 2,
		      Dummy.class,
		      Long.class);
	}
}
