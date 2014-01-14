package com.tmt.kontroll.test.dao.entity.values.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProviderTest;

public class DoubleFieldValueProviderTest extends ValueFieldProviderTest {

	public DoubleFieldValueProviderTest() {
		super(new DoubleFieldValueProvider(), String.class, "blubb");
	}

	@Test
	public void testThatValueProviderIsInitializedCorrectly() {
		assertEquals(0.0, this.toTest.provide(randomFieldName, Double.class));
	}

	@Test
	public void testThatProvisionAndIncrementWorks() {
		assertEquals(0.0, this.toTest.provide(randomFieldName, Double.class));
		assertEquals(1.0, this.toTest.provide(randomFieldName, Double.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueOne() {
		//when
		this.toTest.offset(randomFieldName, 1.0);

		//then
		assertEquals(1.0, this.toTest.provide(randomFieldName, Double.class));
		assertEquals(2.0, this.toTest.provide(randomFieldName, Double.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueZero() {
		//when
		this.toTest.offset(randomFieldName, 0.0);

		//then
		assertEquals(0.0, this.toTest.provide(randomFieldName, Double.class));
		assertEquals(1.0, this.toTest.provide(randomFieldName, Double.class));
	}

	@Test
	public void testThatIncreaseWorksForZeroSteps() {
		//when
		this.toTest.increase(0);

		//then
		assertEquals(0.0, this.toTest.provide(randomFieldName, Double.class));
		assertEquals(1.0, this.toTest.provide(randomFieldName, Double.class));
	}

	@Test
	public void testThatIncreaseWorksForEvenSteps() {
		//when
		this.toTest.increase(10);

		//then
		assertEquals(10.0, this.toTest.provide(randomFieldName, Double.class));
		assertEquals(11.0, this.toTest.provide(randomFieldName, Double.class));
	}

	@Test
	public void testThatIncreaseWorksForOddSteps() {
		//when
		this.toTest.increase(9);

		//then
		assertEquals(9.0, this.toTest.provide(randomFieldName, Double.class));
		assertEquals(10.0, this.toTest.provide(randomFieldName, Double.class));
	}

	@Test
	public void testThatResetWorks() {
		//given
		assertEquals(0.0, this.toTest.provide(randomFieldName, Double.class));
		assertEquals(1.0, this.toTest.provide(randomFieldName, Double.class));

		//when
		this.toTest.reset();

		//then
		assertEquals(0.0, this.toTest.provide(randomFieldName, Double.class));
	}
}
