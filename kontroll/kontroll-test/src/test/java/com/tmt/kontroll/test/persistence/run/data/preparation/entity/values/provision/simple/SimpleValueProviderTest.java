package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

public abstract class SimpleValueProviderTest<V> extends ValueProviderTest<V> {

	protected SimpleValueProviderTest(final ValueProvider<V> toTest,
	                                  final V referenceValue,
	                                  final V referenceNextValue,
	                                  final Class<?>... types) throws Exception {
		super(toTest, referenceValue, referenceNextValue, ValueProvisionKind.ZeroDimensional, types);
	}
}
