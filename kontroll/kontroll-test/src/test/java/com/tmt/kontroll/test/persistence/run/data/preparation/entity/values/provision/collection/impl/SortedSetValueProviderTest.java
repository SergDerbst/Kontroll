package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.CollectionValueProviderTest;

@SuppressWarnings("serial")
public class SortedSetValueProviderTest extends CollectionValueProviderTest<SortedSet<Object>> {

	private static final SortedSet<Object> referenceSet = new TreeSet<Object>() {{
		this.add("0");
	}};
	private static final SortedSet<Object> referenceNextSet = new TreeSet<Object>() {{
		this.add("1");
	}};

	protected SortedSetValueProviderTest(	final ValueProvider<SortedSet<Object>> toTest,
	                                     	final SortedSet<Object> referenceValue,
	                                     	final SortedSet<Object> referenceNextValue,
	                                     	final Class<?>[] types) throws Exception {
		super(new SortedSetValueProvider(ValueProvisionHandler.newInstance()),
		      referenceSet,
		      referenceNextSet,
		      Dummy.class,
		      SortedSet.class,
		      String.class);
	}
}
