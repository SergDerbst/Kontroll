package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id;

import java.util.HashMap;
import java.util.Map;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.impl.IntegerIdValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.impl.LongIdValueProvider;

public class IdValueProviderFactory {

	private static class InstanceHolder {
		public static IdValueProviderFactory instance = new IdValueProviderFactory();
	}

	public static IdValueProviderFactory instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new IdValueProviderFactory();
		}
		return InstanceHolder.instance;
	}

	@SuppressWarnings("serial")
	private static final Map<Class<?>, Class<? extends IdValueProvider<?>>> providerMap = new HashMap<Class<?>, Class<? extends IdValueProvider<?>>>() {{
		this.put(Integer.class, IntegerIdValueProvider.class);
		this.put(Long.class, LongIdValueProvider.class);
	}};

	public IdValueProvider<?> create(final ValueProvisionHandler provisionHandler,
	                                 final Class<?> entityType,
	                                 final Class<?> idType) {
		try {
			return providerMap.get(idType).getConstructor(ValueProvisionHandler.class, Class.class).newInstance(provisionHandler, entityType);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
