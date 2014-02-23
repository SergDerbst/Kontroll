package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl;


import java.util.HashMap;
import java.util.Map;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

@SuppressWarnings("serial")
public class DefaultMapValueProviderTest extends ValueProviderTest<Map<Object, Object>>{

	private static final Map<Object, Object> referenceMap = new HashMap<Object, Object>() {{
		this.put("0", "1");
	}};
	private static final Map<Object, Object> nextReferenceMap = new HashMap<Object, Object>() {{
		this.put("1", "2");
	}};

	public DefaultMapValueProviderTest() throws Exception {
		super(new DefaultMapValueProvider(ValueProvisionHandler.newInstance()),
		      referenceMap,
		      nextReferenceMap,
		      Map.class.getSimpleName(),
		      Dummy.class,
		      Map.class,
		      String.class,
		      String.class);
	}
}
