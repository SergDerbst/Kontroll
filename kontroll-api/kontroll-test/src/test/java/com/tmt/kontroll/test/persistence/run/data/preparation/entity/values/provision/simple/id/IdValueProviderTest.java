package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

public abstract class IdValueProviderTest<V> extends ValueProviderTest<V> {

	protected IdValueProviderTest(final ValueProvider<V> toTest,
	                              final V referenceValue,
	                              final V referenceNextValue,
	                              final Class<?>... types) throws Exception {
		super(toTest, referenceValue, referenceNextValue, ValueProvisionKind.Id, types);
	}
}
