package com.tmt.kontroll.test.dao.entity.values.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProviderTest;

public class FloatFieldValueProviderTest extends ValueFieldProviderTest {

	public FloatFieldValueProviderTest() {
		super(new FloatFieldValueProvider(), String.class, "blubb");
	}

	@Test
	public void testThatValueProviderIsInitializedCorrectly() {
		assertEquals((float) 0.0, this.toTest.provide(randomFieldName, Float.class));
	}

	@Test
	public void testThatProvisionAndIncrementWorks() {
		assertEquals((float) 0.0, this.toTest.provide(randomFieldName, Float.class));
		assertEquals((float) 1.0, this.toTest.provide(randomFieldName, Float.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueOne() {
		//when
		this.toTest.offset(randomFieldName, (float) 1.0);

		//then
		assertEquals((float) 1.0, this.toTest.provide(randomFieldName, Float.class));
		assertEquals((float) 2.0, this.toTest.provide(randomFieldName, Float.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueZero() {
		//when
		this.toTest.offset(randomFieldName, (float) 0.0);

		//then
		assertEquals((float) 0.0, this.toTest.provide(randomFieldName, Float.class));
		assertEquals((float) 1.0, this.toTest.provide(randomFieldName, Float.class));
	}

	@Test
	public void testThatIncreaseWorksForZeroSteps() {
		//when
		this.toTest.increase(0);

		//then
		assertEquals((float) 0.0, this.toTest.provide(randomFieldName, Float.class));
		assertEquals((float) 1.0, this.toTest.provide(randomFieldName, Float.class));
	}

	@Test
	public void testThatIncreaseWorksForEvenSteps() {
		//when
		this.toTest.increase(10);

		//then
		assertEquals((float) 10.0, this.toTest.provide(randomFieldName, Float.class));
		assertEquals((float) 11.0, this.toTest.provide(randomFieldName, Float.class));
	}

	@Test
	public void testThatIncreaseWorksForOddSteps() {
		//when
		this.toTest.increase(9);

		//then
		assertEquals((float) 9.0, this.toTest.provide(randomFieldName, Float.class));
		assertEquals((float) 10.0, this.toTest.provide(randomFieldName, Float.class));
	}

	@Test
	public void testThatResetWorks() {
		//given
		assertEquals((float) 0.0, this.toTest.provide(randomFieldName, Float.class));
		assertEquals((float) 1.0, this.toTest.provide(randomFieldName, Float.class));

		//when
		this.toTest.reset();

		//then
		assertEquals((float) 0.0, this.toTest.provide(randomFieldName, Float.class));
	}
}
