package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.constant;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.responsibility.ValueResponsibilityClaimer;

public class ConstantValueProviderFactory {

	private static class InstanceHolder {
		public static ConstantValueProviderFactory instance;
	}

	public static ConstantValueProviderFactory instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new ConstantValueProviderFactory();
		}
		return InstanceHolder.instance;
	}

	public <V> ConstantValueProvider<V> create(final ValueProvisionHandler provisionHandler,
	                                           final ValueResponsibilityClaimer responsibilityClaimer) {
		final ConstantValueProvider<V> constantValueProvider = new ConstantValueProvider<V>(provisionHandler);
		constantValueProvider.setResponsibilityClaimer(responsibilityClaimer);
		return constantValueProvider;
	}
}
