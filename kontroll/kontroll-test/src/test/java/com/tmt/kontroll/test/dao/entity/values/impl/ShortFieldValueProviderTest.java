package com.tmt.kontroll.test.dao.entity.values.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProviderTest;

public class ShortFieldValueProviderTest extends ValueFieldProviderTest {

	public ShortFieldValueProviderTest() {
		super(new ShortFieldValueProvider(), String.class, "blubb");
	}

	@Test
	public void testThatValueProviderIsInitializedCorrectly() {
		assertEquals((short) 0, this.toTest.provide(randomFieldName, Short.class));
	}

	@Test
	public void testThatProvisionAndIncrementWorks() {
		assertEquals((short) 0, this.toTest.provide(randomFieldName, Short.class));
		assertEquals((short) 1, this.toTest.provide(randomFieldName, Short.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueOne() {
		//when
		this.toTest.offset(randomFieldName, (short) 1);

		//then
		assertEquals((short) 1, this.toTest.provide(randomFieldName, Short.class));
		assertEquals((short) 2, this.toTest.provide(randomFieldName, Short.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueZero() {
		//when
		this.toTest.offset(randomFieldName, (short) 0);

		//then
		assertEquals((short) 0, this.toTest.provide(randomFieldName, Short.class));
		assertEquals((short) 1, this.toTest.provide(randomFieldName, Short.class));
	}

	@Test
	public void testThatIncreaseWorksForZeroSteps() {
		//when
		this.toTest.increase(0);

		//then
		assertEquals((short) 0, this.toTest.provide(randomFieldName, Short.class));
		assertEquals((short) 1, this.toTest.provide(randomFieldName, Short.class));
	}

	@Test
	public void testThatIncreaseWorksForEvenSteps() {
		//when
		this.toTest.increase(10);

		//then
		assertEquals((short) 10, this.toTest.provide(randomFieldName, Short.class));
		assertEquals((short) 11, this.toTest.provide(randomFieldName, Short.class));
	}

	@Test
	public void testThatIncreaseWorksForOddSteps() {
		//when
		this.toTest.increase(9);

		//then
		assertEquals((short) 9, this.toTest.provide(randomFieldName, Short.class));
		assertEquals((short) 10, this.toTest.provide(randomFieldName, Short.class));
	}

	@Test
	public void testThatResetWorks() {
		//given
		assertEquals((short) 0, this.toTest.provide(randomFieldName, Short.class));
		assertEquals((short) 1, this.toTest.provide(randomFieldName, Short.class));

		//when
		this.toTest.reset();

		//then
		assertEquals((short) 0, this.toTest.provide(randomFieldName, Short.class));
	}
}
