package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl;

import java.util.HashSet;
import java.util.Set;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.CollectionValueProviderTest;

@SuppressWarnings("serial")
public class SetValueProviderTest extends CollectionValueProviderTest<Set<Object>> {

	private static final Set<Object> referenceSet = new HashSet<Object>() {{
		this.add("0");
	}};
	private static final Set<Object> referenceNextSet = new HashSet<Object>() {{
		this.add("1");
	}};

	public SetValueProviderTest() throws Exception {
		super(new SetValueProvider(ValueProvisionHandler.newInstance()),
		      referenceSet,
		      referenceNextSet,
		      Dummy.class,
		      Set.class,
		      String.class);
	}
}
