package com.tmt.kontroll.content.persistence.conversion.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.content.persistence.conversion.ValueStringConverter;

public abstract class ValueStringConverterTest<R, N> {

	@Mock
	private ValueStringConverter<?> nextConverter;

	private final Class<R> responsibleValueType;
	private final R responsibleValue;
	private final String responsibleString;
	private final Class<N> notResponsibleValueType;
	private final N notResponsibleValue;
	private final String notResponsibleString;
	
	protected final ValueStringConverter<R> toTest;

	protected ValueStringConverterTest(	final ValueStringConverter<R> toTest,
																			final Class<R> responsibleValueType,
																			final R responsibleValue,
																			final String responsibleString,
																			final Class<N> notResponsibleValueType,
																			final N notResponsibleValue,
																			final String notResponsibleString) {
		this.toTest = toTest;
		this.responsibleValueType = responsibleValueType;
		this.responsibleString = responsibleString;
		this.responsibleValue = responsibleValue;
		this.notResponsibleValueType = notResponsibleValueType;
		this.notResponsibleString = notResponsibleString;
		this.notResponsibleValue = notResponsibleValue;
	}

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test(expected = RuntimeException.class)
	public void testThatExceptionIsThrownForWhenNextConverterIsNullWithConvertToString() {
		//given
		this.toTest.setNextConverter(null);
		//when
		this.toTest.convertToString(this.notResponsibleValue);
	}
	
	@Test(expected = RuntimeException.class)
	public void testThatExceptionIsThrownForWhenNextConverterIsNullWithConvertToValue() {
		//given
		this.toTest.setNextConverter(null);
		//when
		this.toTest.convertToValue(this.notResponsibleString, this.notResponsibleValueType);
	}

	@Test
	public void testThatNextConverterIsCalledForConvertToString() {
		//given
		this.toTest.setNextConverter(this.nextConverter);
		//when
		this.toTest.convertToString(this.notResponsibleValue);
		//then
		verify(this.nextConverter).convertToString(this.notResponsibleValue);
	}

	@Test
	public void testThatNextConverterIsCalledForConvertToValue() {
		//given
		this.toTest.setNextConverter(this.nextConverter);
		//when
		this.toTest.convertToValue(this.notResponsibleString, this.notResponsibleValueType);
		//then
		verify(this.nextConverter).convertToValue(this.notResponsibleString, this.notResponsibleValueType);
	}

	@Test
	public void testThatNullIsConvertedToNullString() {
		assertEquals("null", this.toTest.convertToString((R) null));
	}

	@Test
	public void testThatNullIsConvertedToNull() {
		assertNull(this.toTest.convertToValue(null, this.responsibleValueType));
	}

	@Test
	public void testThatEmptyStringIsConvertedToNull() {
		assertNull(this.toTest.convertToValue("", this.responsibleValueType));
	}

	@Test
	public void testThatNullStringIsConvertedToNull() {
		assertNull(this.toTest.convertToValue("null", this.responsibleValueType));
	}

	@Test
	public void testThatValueIsConvertedToString() {
		assertEquals(this.responsibleString, this.toTest.convertToString(this.responsibleValue));
	}

	@Test
	public void testThatStringIsConvertedToValue() {
		assertEquals(this.responsibleValue, this.toTest.convertToValue(this.responsibleString, this.responsibleValueType));
	}
}
