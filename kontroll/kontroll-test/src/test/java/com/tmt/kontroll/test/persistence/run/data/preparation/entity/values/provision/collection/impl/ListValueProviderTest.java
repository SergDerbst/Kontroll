package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

@SuppressWarnings("serial")
public class ListValueProviderTest extends ValueProviderTest<List<Object>> {

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
		      List.class.getSimpleName(),
		      Dummy.class,
		      List.class,
		      String.class);
	}
}
