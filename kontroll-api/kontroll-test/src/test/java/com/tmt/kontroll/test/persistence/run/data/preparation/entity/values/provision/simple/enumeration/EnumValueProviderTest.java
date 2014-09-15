package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.enumeration;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProviderTest;

public class EnumValueProviderTest extends SimpleValueProviderTest<Enum<?>> {

	public EnumValueProviderTest() throws Exception {
		super(new EnumValueProvider(ValueProvisionHandler.newInstance(), DummyEnum.class),
		      DummyEnum.Dumb,
		      DummyEnum.Dumber,
		      Dummy.class,
		      DummyEnum.class);
	}
}
