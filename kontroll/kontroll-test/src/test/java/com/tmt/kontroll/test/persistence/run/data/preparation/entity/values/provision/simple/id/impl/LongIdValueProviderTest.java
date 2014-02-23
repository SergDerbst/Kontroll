package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class LongIdValueProviderTest extends ValueProviderTest<Long> {

	public LongIdValueProviderTest() throws Exception {
		super(new LongIdValueProvider(ValueProvisionHandler.newInstance(), Dummy.class),
		      (long) 1,
		      (long) 2,
		      Long.class.getSimpleName() + "Id",
		      Dummy.class,
		      Long.class);
	}
}
