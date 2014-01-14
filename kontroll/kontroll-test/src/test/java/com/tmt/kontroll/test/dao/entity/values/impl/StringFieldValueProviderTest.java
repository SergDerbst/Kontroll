package com.tmt.kontroll.test.dao.entity.values.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProviderTest;

public class StringFieldValueProviderTest extends ValueFieldProviderTest {

	public StringFieldValueProviderTest() {
		super(new StringFieldValueProvider(), Integer.class, 0);
	}

	@Test
	public void testThatValueProviderIsInitializedCorrectly() {
		assertEquals("0", this.toTest.provide(randomFieldName, String.class));
	}

	@Test
	public void testThatProvisionAndIncrementWorks() {
		assertEquals("0", this.toTest.provide(randomFieldName, String.class));
		assertEquals("1", this.toTest.provide(randomFieldName, String.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueOne() {
		//when
		this.toTest.offset(randomFieldName, "1");

		//then
		assertEquals("10", this.toTest.provide(randomFieldName, String.class));
		assertEquals("11", this.toTest.provide(randomFieldName, String.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueZero() {
		//when
		this.toTest.offset(randomFieldName, "0");

		//then
		assertEquals("00", this.toTest.provide(randomFieldName, String.class));
		assertEquals("01", this.toTest.provide(randomFieldName, String.class));
	}

	@Test
	public void testThatIncreaseWorksForZeroSteps() {
		//when
		this.toTest.increase(0);

		//then
		assertEquals("0", this.toTest.provide(randomFieldName, String.class));
		assertEquals("1", this.toTest.provide(randomFieldName, String.class));
	}

	@Test
	public void testThatIncreaseWorksForEvenSteps() {
		//when
		this.toTest.increase(10);

		//then
		assertEquals("10", this.toTest.provide(randomFieldName, String.class));
		assertEquals("11", this.toTest.provide(randomFieldName, String.class));
	}

	@Test
	public void testThatIncreaseWorksForOddSteps() {
		//when
		this.toTest.increase(9);

		//then
		assertEquals("9", this.toTest.provide(randomFieldName, String.class));
		assertEquals("10", this.toTest.provide(randomFieldName, String.class));
	}

	@Test
	public void testThatResetWorks() {
		//given
		assertEquals("0", this.toTest.provide(randomFieldName, String.class));
		assertEquals("1", this.toTest.provide(randomFieldName, String.class));

		//when
		this.toTest.reset();

		//then
		assertEquals("0", this.toTest.provide(randomFieldName, String.class));
	}
}
