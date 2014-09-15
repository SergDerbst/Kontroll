package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProviderTest;

public class CharacterValueProviderTest extends SimpleValueProviderTest<Character> {

	public CharacterValueProviderTest() throws Exception {
		super(new CharacterValueProvider(ValueProvisionHandler.newInstance()),
		      "0".charAt(0),
		      "1".charAt(0),
		      Dummy.class,
		      Character.class);
	}
}
