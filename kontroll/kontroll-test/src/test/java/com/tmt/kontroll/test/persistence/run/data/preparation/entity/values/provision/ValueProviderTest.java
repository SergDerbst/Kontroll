package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.test.persistence.run.utils.exceptions.value.provision.ValueProviderNotFoundException;

public abstract class ValueProviderTest<V> {

	public enum DummyEnum {
		Dumb, Dumber;
	}

	public static class Dummy {}

	@Mock
	protected ValueProvider<?> nextProvider;

	protected final ValueProvider<V> toTest;
	protected final V referenceValue;
	protected final V referenceNextValue;
	protected final Class<?>[] types;
	protected final ValueProvisionKind provisionKind;

	protected ValueProviderTest(final ValueProvider<V> toTest,
	                            final V referenceValue,
	                            final V referenceNextValue,
	                            final ValueProvisionKind provisionKind,
	                            final Class<?>... types) throws Exception {
		this.toTest = toTest;
		this.referenceValue = referenceValue;
		this.referenceNextValue = referenceNextValue;
		this.types = types;
		this.provisionKind = provisionKind;
	}

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		ValueProvisionPreparer.newInstance().prepare(this.toTest.valueProvisionHandler(), this.provisionKind, new Dummy(), this.types);
		this.toTest.setNextProvider(this.nextProvider);
	}

	@Test
	public void testThatFetchValueProviderTypeWorks() throws Exception {
		//when
		final Class<? extends ValueProvider<?>> fetched = this.toTest.fetchValueProviderType(this.provisionKind, this.types);
		//then
		assertEquals(this.toTest.getClass(), fetched);
		verify(this.nextProvider, never()).fetchValueProviderType(this.provisionKind, this.types);
	}

	@Test
	public void testThatFetchValueProviderTypeCallsNextValue() throws Exception {
		//when
		this.toTest.fetchValueProviderType(this.provisionKind, this.getClass(), this.getClass());
		//then
		verify(this.nextProvider).fetchValueProviderType(this.provisionKind, this.getClass(), this.getClass());
	}

	@Test(expected = ValueProviderNotFoundException.class)
	public void testThatFetchValueProviderTypeThrowsExceptionWhenNextProviderIsNull() throws Exception {
		//given
		this.toTest.setNextProvider(null);
		//when
		this.toTest.fetchValueProviderType(this.provisionKind, this.getClass(), this.getClass());
	}

	@Test
	public void testThatCanProvideValueWorks() throws Exception {
		//when
		final boolean can = this.toTest.canProvideValue(this.provisionKind, this.types);
		//then
		assertTrue(can);
		verify(this.nextProvider, never()).canProvideValue(this.provisionKind, this.types);
	}

	@Test
	public void testThatCanProvideValueCallsNextValue() throws Exception {
		//when
		this.toTest.canProvideValue(this.provisionKind, this.getClass(), this.getClass());
		//then
		verify(this.nextProvider).canProvideValue(this.provisionKind, this.getClass(), this.getClass());
	}

	@Test
	public void testThatCanProvideValueDoesNotThrowExceptionWhenNextProviderIsNull() throws Exception {
		//given
		this.toTest.setNextProvider(null);
		//when
		final boolean can = this.toTest.canProvideValue(this.provisionKind, this.getClass(), this.getClass());
		//then
		assertFalse(can);
		verify(this.nextProvider, never()).canProvideValue(this.provisionKind, this.getClass(), this.getClass());
	}

	@Test
	public void testThatProvideWorks() throws Exception {
		//when
		final Object provided = this.toTest.provide(new Dummy(), this.provisionKind, this.types);
		//then
		assertEquals(this.referenceValue, provided);
		verify(this.nextProvider, never()).provide(new Dummy(), this.provisionKind, this.types);
	}

	@Test
	public void testThatProvideCallsNextValue() throws Exception {
		final Dummy dummy = new Dummy();
		//when
		this.toTest.provide(dummy, this.provisionKind, this.getClass(), this.getClass());
		//then
		verify(this.nextProvider).provide(dummy, this.provisionKind, this.getClass(), this.getClass());
	}

	@Test(expected = ValueProviderNotFoundException.class)
	public void testThatProvideThrowsExceptionWhenNextProviderIsNull() throws Exception {
		//given
		this.toTest.setNextProvider(null);
		//when
		this.toTest.provide(new Dummy(), this.provisionKind, this.getClass(), this.getClass());
	}

	@Test
	public void testThatFetchNextValueWorks() throws Exception {
		//when
		final Object fetched = this.toTest.fetchNextZeroDimensionalValue(new Dummy(), this.referenceValue);
		//then
		assertEquals(this.referenceNextValue, fetched);
		verify(this.nextProvider, never()).fetchNextZeroDimensionalValue(new Dummy(), this.referenceValue);
	}

	@Test
	public void testThatFetchNextValueCallsNextValue() throws Exception {
		//when
		this.toTest.fetchNextZeroDimensionalValue(this.getClass(), this.getClass());
		//then
		verify(this.nextProvider).fetchNextZeroDimensionalValue(this.getClass(), this.getClass());
	}

	@Test(expected = ValueProviderNotFoundException.class)
	public void testThatFetchNextValueThrowsExceptionWhenNextProviderIsNull() throws Exception {
		this.toTest.setNextProvider(null);
		//when
		this.toTest.fetchNextZeroDimensionalValue(this.getClass(), this.getClass());
	}
}
