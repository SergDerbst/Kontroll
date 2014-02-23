package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class CharacterValueProviderTest extends ValueProviderTest<Character> {

	public CharacterValueProviderTest() throws Exception {
		super(new CharacterValueProvider(ValueProvisionHandler.newInstance()),
		      "0".charAt(0),
		      "1".charAt(0),
		      Character.class.getSimpleName(),
		      Dummy.class,
		      Character.class);
	}
}
