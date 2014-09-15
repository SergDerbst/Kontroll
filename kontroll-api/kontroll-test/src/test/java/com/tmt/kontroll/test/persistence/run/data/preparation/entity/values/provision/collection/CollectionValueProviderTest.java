package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection;

import java.util.Collection;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

public abstract class CollectionValueProviderTest<C extends Collection<?>> extends ValueProviderTest<C> {

	protected CollectionValueProviderTest(final ValueProvider<C> toTest,
	                                      final C referenceValue,
	                                      final C referenceNextValue,
	                                      final Class<?>... types) throws Exception {
		super(toTest, referenceValue, referenceNextValue, ValueProvisionKind.OneDimensional, types);
	}
}
