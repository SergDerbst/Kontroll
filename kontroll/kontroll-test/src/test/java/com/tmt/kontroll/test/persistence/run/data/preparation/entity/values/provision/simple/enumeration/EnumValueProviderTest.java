package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.enumeration;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class EnumValueProviderTest extends ValueProviderTest<Enum<?>> {

	public EnumValueProviderTest() throws Exception {
		super(new EnumValueProvider(ValueProvisionHandler.newInstance(), DummyEnum.class),
		      DummyEnum.Dumb,
		      DummyEnum.Dumber,
		      Enum.class.getSimpleName(),
		      Dummy.class,
		      DummyEnum.class);
	}
}
