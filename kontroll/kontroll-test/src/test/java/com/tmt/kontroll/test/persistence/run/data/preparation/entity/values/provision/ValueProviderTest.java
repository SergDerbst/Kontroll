package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import javax.persistence.Id;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.test.persistence.run.utils.exceptions.value.provision.ValueProviderNotFoundException;

public abstract class ValueProviderTest<V> {

	public enum DummyEnum {
		Dumb, Dumber;
	}

	public static class Dummy {
		@Id
		public Integer dummyIntegerId;
		@Id
		public Long dummyLongId;
		public Boolean dummyBoolean;
		public Byte dummyByte;
		public Character dummyCharacter;
		public Double dummyDouble;
		public Float dummyFloat;
		public Integer dummyInteger;
		public Locale dummyLocale;
		public Long dummyLong;
		public Short dummyShort;
		public String dummyString;
		public Timestamp dummyTimestamp;
		public DummyEnum dummyEnum;
		public String[] dummyArray;
		public List<String> dummyList;
		public Set<String> dummySet;
		public SortedSet<String> dummySortedSet;
		public Map<String, String> dummyMap;
		public EnumMap<DummyEnum, String> dummyEnumMap;
		public SortedMap<String, String> dummySortedMap;
	}

	@Mock
	protected ValueProvider<?> nextProvider;

	protected final ValueProvider<V> toTest;
	protected final V referenceValue;
	protected final V referenceNextValue;
	protected final Class<?>[] types;
	protected final Field field;

	protected ValueProviderTest(final ValueProvider<V> toTest,
	                            final V referenceValue,
	                            final V referenceNextValue,
	                            final String typeName,
	                            final Class<?>... types) throws Exception {
		this.toTest = toTest;
		this.referenceValue = referenceValue;
		this.referenceNextValue = referenceNextValue;
		this.types = types;
		this.field = Dummy.class.getDeclaredField("dummy" + typeName);
	}

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		ValueProvisionPreparer.newInstance().prepare(this.toTest.valueProvisionHandler(), this.field, new Dummy(), this.types);
		this.toTest.setNextProvider(this.nextProvider);
	}

	@Test
	public void testThatFetchValueProviderTypeWorks() throws Exception {
		//when
		final Class<? extends ValueProvider<?>> fetched = this.toTest.fetchValueProviderType(this.field, this.types);
		//then
		assertEquals(this.toTest.getClass(), fetched);
		verify(this.nextProvider, never()).fetchValueProviderType(this.field, this.types);
	}

	@Test
	public void testThatFetchValueProviderTypeCallsNextValue() throws Exception {
		//when
		this.toTest.fetchValueProviderType(this.field, this.getClass(), this.getClass());
		//then
		verify(this.nextProvider).fetchValueProviderType(this.field, this.getClass(), this.getClass());
	}

	@Test(expected = ValueProviderNotFoundException.class)
	public void testThatFetchValueProviderTypeThrowsExceptionWhenNextProviderIsNull() throws Exception {
		//given
		this.toTest.setNextProvider(null);
		//when
		this.toTest.fetchValueProviderType(this.field, this.getClass(), this.getClass());
	}

	@Test
	public void testThatCanProvideValueWorks() throws Exception {
		//when
		final boolean can = this.toTest.canProvideValue(this.field, this.types);
		//then
		assertTrue(can);
		verify(this.nextProvider, never()).canProvideValue(this.field, this.types);
	}

	@Test
	public void testThatCanProvideValueCallsNextValue() throws Exception {
		//when
		this.toTest.canProvideValue(this.field, this.getClass(), this.getClass());
		//then
		verify(this.nextProvider).canProvideValue(this.field, this.getClass(), this.getClass());
	}

	@Test
	public void testThatCanProvideValueDoesNotThrowExceptionWhenNextProviderIsNull() throws Exception {
		//given
		this.toTest.setNextProvider(null);
		//when
		final boolean can = this.toTest.canProvideValue(this.field, this.getClass(), this.getClass());
		//then
		assertFalse(can);
		verify(this.nextProvider, never()).canProvideValue(this.field, this.getClass(), this.getClass());
	}

	@Test
	public void testThatProvideWorks() throws Exception {
		//when
		final Object provided = this.toTest.provide(new Dummy(), this.field, this.types);
		//then
		assertEquals(this.referenceValue, provided);
		verify(this.nextProvider, never()).provide(new Dummy(), this.field, this.types);
	}

	@Test
	public void testThatProvideCallsNextValue() throws Exception {
		final Dummy dummy = new Dummy();
		//when
		this.toTest.provide(dummy, this.field, this.getClass(), this.getClass());
		//then
		verify(this.nextProvider).provide(dummy, this.field, this.getClass(), this.getClass());
	}

	@Test(expected = ValueProviderNotFoundException.class)
	public void testThatProvideThrowsExceptionWhenNextProviderIsNull() throws Exception {
		//given
		this.toTest.setNextProvider(null);
		//when
		this.toTest.provide(new Dummy(), this.field, this.getClass(), this.getClass());
	}

	@Test
	public void testThatFetchNextValueWorks() throws Exception {
		//when
		final Object fetched = this.toTest.fetchNextValue(new Dummy(), this.field, this.referenceValue);
		//then
		assertEquals(this.referenceNextValue, fetched);
		verify(this.nextProvider, never()).fetchNextValue(new Dummy(), this.field, this.referenceValue);
	}

	@Test
	public void testThatFetchNextValueCallsNextValue() throws Exception {
		//when
		this.toTest.fetchNextValue(this.getClass(), this.field, this.getClass());
		//then
		verify(this.nextProvider).fetchNextValue(this.getClass(), this.field, this.getClass());
	}

	@Test(expected = ValueProviderNotFoundException.class)
	public void testThatFetchNextValueThrowsExceptionWhenNextProviderIsNull() throws Exception {
		this.toTest.setNextProvider(null);
		//when
		this.toTest.fetchNextValue(this.getClass(), this.field, this.getClass());
	}
}
