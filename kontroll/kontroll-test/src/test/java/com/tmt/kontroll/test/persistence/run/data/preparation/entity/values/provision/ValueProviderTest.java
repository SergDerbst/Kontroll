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
	protected final Class<?>[] responsibleTypes;
	protected final Class<?>[] notResponsibleTypes;
	protected final ValueProvisionKind provisionKind;

	protected ValueProviderTest(final ValueProvider<V> toTest,
	                            final V referenceValue,
	                            final V referenceNextValue,
	                            final ValueProvisionKind provisionKind,
	                            final Class<?>... types) throws Exception {
		this.toTest = toTest;
		this.referenceValue = referenceValue;
		this.referenceNextValue = referenceNextValue;
		this.provisionKind = provisionKind;
		this.responsibleTypes = types;
		this.notResponsibleTypes = this.prepareNonResponsibleTypes(types);
	}

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		ValueProvisionPreparer.newInstance().prepare(this.toTest.valueProvisionHandler(), this.provisionKind, new Dummy(), this.responsibleTypes);
		this.toTest.setNextProvider(this.nextProvider);
	}

	@Test
	public void testThatFetchValueProviderTypeWorks() throws Exception {
		//when
		final Class<? extends ValueProvider<?>> fetched = this.toTest.fetchValueProviderType(this.provisionKind, this.responsibleTypes);
		//then
		assertEquals(this.toTest.getClass(), fetched);
		verify(this.nextProvider, never()).fetchValueProviderType(this.provisionKind, this.responsibleTypes);
	}

	@Test
	public void testThatFetchValueProviderTypeCallsNextValue() throws Exception {
		//when
		this.toTest.fetchValueProviderType(this.provisionKind, this.notResponsibleTypes);
		//then
		verify(this.nextProvider).fetchValueProviderType(this.provisionKind, this.notResponsibleTypes);
	}

	@Test(expected = ValueProviderNotFoundException.class)
	public void testThatFetchValueProviderTypeThrowsExceptionWhenNextProviderIsNull() throws Exception {
		//given
		this.toTest.setNextProvider(null);
		//when
		this.toTest.fetchValueProviderType(this.provisionKind, this.notResponsibleTypes);
	}

	@Test
	public void testThatCanProvideValueWorks() throws Exception {
		//when
		final boolean can = this.toTest.canProvideValue(this.provisionKind, this.responsibleTypes);
		//then
		assertTrue(can);
		verify(this.nextProvider, never()).canProvideValue(this.provisionKind, this.responsibleTypes);
	}

	@Test
	public void testThatCanProvideValueCallsNextValue() throws Exception {
		//when
		this.toTest.canProvideValue(this.provisionKind, this.notResponsibleTypes);
		//then
		verify(this.nextProvider).canProvideValue(this.provisionKind, this.notResponsibleTypes);
	}

	@Test
	public void testThatCanProvideValueDoesNotThrowExceptionWhenNextProviderIsNull() throws Exception {
		//given
		this.toTest.setNextProvider(null);
		//when
		final boolean can = this.toTest.canProvideValue(this.provisionKind, this.notResponsibleTypes);
		//then
		assertFalse(can);
		verify(this.nextProvider, never()).canProvideValue(this.provisionKind, this.notResponsibleTypes);
	}

	@Test
	public void testThatProvideWorks() throws Exception {
		//when
		final Object provided = this.toTest.provide(new Dummy(), this.provisionKind, this.responsibleTypes);
		//then
		assertEquals(this.referenceValue, provided);
		verify(this.nextProvider, never()).provide(new Dummy(), this.provisionKind, this.responsibleTypes);
	}

	@Test
	public void testThatProvideCallsNextValue() throws Exception {
		final Dummy dummy = new Dummy();
		//when
		this.toTest.provide(dummy, this.provisionKind, this.notResponsibleTypes);
		//then
		verify(this.nextProvider).provide(dummy, this.provisionKind, this.notResponsibleTypes);
	}

	@Test(expected = ValueProviderNotFoundException.class)
	public void testThatProvideThrowsExceptionWhenNextProviderIsNull() throws Exception {
		//given
		this.toTest.setNextProvider(null);
		//when
		this.toTest.provide(new Dummy(), this.provisionKind, this.notResponsibleTypes);
	}

	@Test
	public void testThatFetchNextValueWorks() throws Exception {
		//when
		final Object fetched = this.toTest.fetchNextValue(new Dummy(), this.provisionKind, this.referenceValue, this.responsibleTypes);
		//then
		assertEquals(this.referenceNextValue, fetched);
		verify(this.nextProvider, never()).fetchNextValue(new Dummy(), this.provisionKind, this.referenceValue, this.responsibleTypes);
	}

	@Test
	public void testThatFetchNextValueCallsNextValue() throws Exception {
		//when
		this.toTest.fetchNextValue(this.getClass(), this.provisionKind, this.referenceValue, this.notResponsibleTypes);
		//then
		verify(this.nextProvider).fetchNextValue(this.getClass(), this.provisionKind, this.referenceValue, this.notResponsibleTypes);
	}

	@Test(expected = ValueProviderNotFoundException.class)
	public void testThatFetchNextValueThrowsExceptionWhenNextProviderIsNull() throws Exception {
		this.toTest.setNextProvider(null);
		//when
		this.toTest.fetchNextValue(this.getClass(), this.provisionKind, this.referenceValue, this.notResponsibleTypes);
	}

	private Class<?>[] prepareNonResponsibleTypes(final Class<?>[] types) {
		final Class<?>[] a = new Class<?>[types.length];
		for (int i = 0; i < types.length; i++) {
			a[i] = this.getClass();
		}
		return a;
	}
}
