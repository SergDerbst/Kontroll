package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class ByteValueProviderTest extends ValueProviderTest<Byte> {

	public ByteValueProviderTest() throws Exception {
		super(new ByteValueProvider(ValueProvisionHandler.newInstance()),
		      Byte.parseByte("0"),
		      Byte.parseByte("1"),
		      Byte.class.getSimpleName(),
		      Dummy.class,
		      Byte.class);
	}
}
