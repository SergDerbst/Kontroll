package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

public class MapValueProviderTest<M> extends ValueProviderTest<M> {

	protected MapValueProviderTest(	final ValueProvider<M> toTest,
	                               	final M referenceValue,
	                               	final M referenceNextValue,
	                               	final Class<?>... types) throws Exception {
		super(toTest, referenceValue, referenceNextValue, ValueProvisionKind.TwoDimensional, types);
	}
}
