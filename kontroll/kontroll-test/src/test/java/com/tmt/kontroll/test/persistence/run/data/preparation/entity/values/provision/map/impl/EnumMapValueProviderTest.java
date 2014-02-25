package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl;

import java.util.EnumMap;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.MapValueProviderTest;

@SuppressWarnings("serial")
public class EnumMapValueProviderTest<E extends Enum<E>> extends MapValueProviderTest<EnumMap<ValueProviderTest.DummyEnum, Object>> {

	private static final EnumMap<DummyEnum, Object> referenceMap = new EnumMap<DummyEnum, Object>(DummyEnum.class) {{
		this.put(DummyEnum.Dumb, "0");
	}};
	private static final EnumMap<DummyEnum, Object> nextReferenceMap = new EnumMap<DummyEnum, Object>(DummyEnum.class) {{
		this.put(DummyEnum.Dumber, "1");
	}};

	public EnumMapValueProviderTest() throws Exception {
		super(new EnumMapValueProvider<DummyEnum>(DummyEnum.class, ValueProvisionHandler.newInstance()),
		      referenceMap,
		      nextReferenceMap,
		      Dummy.class,
		      EnumMap.class,
		      DummyEnum.class,
		      String.class);
	}
}
