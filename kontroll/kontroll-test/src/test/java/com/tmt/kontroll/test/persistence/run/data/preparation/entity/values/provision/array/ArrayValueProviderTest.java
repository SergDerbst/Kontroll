package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.array;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

public class ArrayValueProviderTest extends ValueProviderTest<String[]> {

	public ArrayValueProviderTest() throws Exception {
		super(new ArrayValueProvider<String>(String.class, ValueProvisionHandler.newInstance()),
		      new String[] {"0"},
		      new String[] {"1"},
		      ValueProvisionKind.OneDimensional,
		      Dummy.class,
		      String[].class,
		      String.class);
	}

	@Override
	@Test
	public void testThatProvideWorks() throws Exception {
		//when
		final Object provided = this.toTest.provide(new Dummy(), ValueProvisionKind.OneDimensional, this.types);
		//then
		assertEquals(this.referenceValue[0], ((String[]) provided)[0]);
		verify(this.nextProvider, never()).provide(new Dummy(), ValueProvisionKind.OneDimensional, this.types);
	}

	@Override
	@Test
	public void testThatFetchNextValueWorks() throws Exception {
		//when
		final Object fetched = this.toTest.fetchNextZeroDimensionalValue(new Dummy(), this.referenceValue);
		//then
		assertEquals(this.referenceNextValue[0], ((String[]) fetched)[0]);
		verify(this.nextProvider, never()).fetchNextZeroDimensionalValue(new Dummy(), this.referenceValue);
	}
}
