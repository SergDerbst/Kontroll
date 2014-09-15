package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.CollectionValueProviderTest;

@SuppressWarnings("serial")
public class ListValueProviderTest extends CollectionValueProviderTest<List<Object>> {

	private static final List<Object> referenceList = new ArrayList<Object>() {{
		this.add("0");
	}};
	private static final List<Object> referenceNextList = new ArrayList<Object>() {{
		this.add("1");
	}};

	public ListValueProviderTest() throws Exception {
		super(new ListValueProvider(ValueProvisionHandler.newInstance()),
		      referenceList,
		      referenceNextList,
		      Dummy.class,
		      List.class,
		      String.class);
	}
}
