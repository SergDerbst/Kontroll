package com.tmt.kontroll.test.persistence.dao.entity.value.provision;

import com.tmt.kontroll.test.persistence.dao.entity.value.incrementation.ValueIncrementor;
import com.tmt.kontroll.test.persistence.dao.entity.value.instantiation.ValueInstantiator;
import com.tmt.kontroll.test.persistence.dao.entity.value.responsibility.ValueHandlingResponsibilityClaimer;


public class ValueProviderFactory {

	private static class InstanceHolder {
		public static ValueProviderFactory instance = new ValueProviderFactory();
	}

	public static ValueProviderFactory instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new ValueProviderFactory();
		}
		return InstanceHolder.instance;
	}

	@SuppressWarnings("unchecked")
	public <V> ValueProvider<V> create(final ValueInstantiator<V> instantiator,
	                                   final ValueIncrementor<V> incrementor,
	                                   final ValueHandlingResponsibilityClaimer responsibilityClaimer,
	                                   final String fieldName,
	                                   final Class<?>... types) {
		try {
			final ValueProvider<V> valueProvider = (ValueProvider<V>) ValueProvisionHandler.instance().fetchValueProviderType(fieldName, types).newInstance();
			valueProvider.setInstantiator(instantiator);
			valueProvider.setIncrementor(incrementor);
			valueProvider.setResponsibilityClaimer(responsibilityClaimer);
			return valueProvider;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
