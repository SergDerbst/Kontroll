package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl;

import java.util.SortedMap;
import java.util.TreeMap;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.MapValueProviderTest;

@SuppressWarnings("serial")
public class SortedMapValueProviderTest extends MapValueProviderTest<SortedMap<Object, Object>> {

	private static final SortedMap<Object, Object> referenceMap = new TreeMap<Object, Object>() {{
		this.put("0", "1");
	}};
	private static final SortedMap<Object, Object> nextReferenceMap = new TreeMap<Object, Object>() {{
		this.put("1", "2");
	}};

	public SortedMapValueProviderTest() throws Exception {
		super(new SortedMapValueProvider(ValueProvisionHandler.newInstance()),
		      referenceMap,
		      nextReferenceMap,
		      Dummy.class,
		      SortedMap.class,
		      String.class,
		      String.class);
	}
}
