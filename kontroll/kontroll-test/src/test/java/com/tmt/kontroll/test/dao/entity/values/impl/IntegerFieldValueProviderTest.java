package com.tmt.kontroll.test.dao.entity.values.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProviderTest;

public class IntegerFieldValueProviderTest extends ValueFieldProviderTest {

	public IntegerFieldValueProviderTest() {
		super(new IntegerFieldValueProvider(), String.class, "blubb");
	}

	@Test
	public void testThatValueProviderIsInitializedCorrectly() {
		assertEquals(0, this.toTest.provide(randomFieldName, Integer.class));
	}

	@Test
	public void testThatProvisionAndIncrementWorks() {
		assertEquals(0, this.toTest.provide(randomFieldName, Integer.class));
		assertEquals(1, this.toTest.provide(randomFieldName, Integer.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueOne() {
		//when
		this.toTest.offset(randomFieldName, 1);

		//then
		assertEquals(1, this.toTest.provide(randomFieldName, Integer.class));
		assertEquals(2, this.toTest.provide(randomFieldName, Integer.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueZero() {
		//when
		this.toTest.offset(randomFieldName, 0);

		//then
		assertEquals(0, this.toTest.provide(randomFieldName, Integer.class));
		assertEquals(1, this.toTest.provide(randomFieldName, Integer.class));
	}

	@Test
	public void testThatIncreaseWorksForZeroSteps() {
		//when
		this.toTest.increase(0);

		//then
		assertEquals(0, this.toTest.provide(randomFieldName, Integer.class));
		assertEquals(1, this.toTest.provide(randomFieldName, Integer.class));
	}

	@Test
	public void testThatIncreaseWorksForEvenSteps() {
		//when
		this.toTest.increase(10);

		//then
		assertEquals(10, this.toTest.provide(randomFieldName, Integer.class));
		assertEquals(11, this.toTest.provide(randomFieldName, Integer.class));
	}

	@Test
	public void testThatIncreaseWorksForOddSteps() {
		//when
		this.toTest.increase(9);

		//then
		assertEquals(9, this.toTest.provide(randomFieldName, Integer.class));
		assertEquals(10, this.toTest.provide(randomFieldName, Integer.class));
	}

	@Test
	public void testThatResetWorks() {
		//given
		assertEquals(0, this.toTest.provide(randomFieldName, Integer.class));
		assertEquals(1, this.toTest.provide(randomFieldName, Integer.class));

		//when
		this.toTest.reset();

		//then
		assertEquals(0, this.toTest.provide(randomFieldName, Integer.class));
	}
}
