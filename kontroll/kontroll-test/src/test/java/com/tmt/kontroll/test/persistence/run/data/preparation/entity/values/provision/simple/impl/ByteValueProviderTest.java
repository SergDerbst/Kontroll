package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProviderTest;

public class ByteValueProviderTest extends SimpleValueProviderTest<Byte> {

	public ByteValueProviderTest() throws Exception {
		super(new ByteValueProvider(ValueProvisionHandler.newInstance()),
		      Byte.parseByte("0"),
		      Byte.parseByte("1"),
		      Dummy.class,
		      Byte.class);
	}
}
