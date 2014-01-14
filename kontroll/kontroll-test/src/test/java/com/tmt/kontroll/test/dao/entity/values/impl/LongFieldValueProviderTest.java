package com.tmt.kontroll.test.dao.entity.values.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProviderTest;

public class LongFieldValueProviderTest extends ValueFieldProviderTest {

	public LongFieldValueProviderTest() {
		super(new LongFieldValueProvider(), String.class, "blubb");
	}

	@Test
	public void testThatValueProviderIsInitializedCorrectly() {
		assertEquals((long) 0, this.toTest.provide(randomFieldName, Long.class));
	}

	@Test
	public void testThatProvisionAndIncrementWorks() {
		assertEquals((long) 0, this.toTest.provide(randomFieldName, Long.class));
		assertEquals((long) 1, this.toTest.provide(randomFieldName, Long.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueOne() {
		//when
		this.toTest.offset(randomFieldName, (long) 1);

		//then
		assertEquals((long) 1, this.toTest.provide(randomFieldName, Long.class));
		assertEquals((long) 2, this.toTest.provide(randomFieldName, Long.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueZero() {
		//when
		this.toTest.offset(randomFieldName, (long) 0);

		//then
		assertEquals((long) 0, this.toTest.provide(randomFieldName, Long.class));
		assertEquals((long) 1, this.toTest.provide(randomFieldName, Long.class));
	}

	@Test
	public void testThatIncreaseWorksForZeroSteps() {
		//when
		this.toTest.increase(0);

		//then
		assertEquals((long) 0, this.toTest.provide(randomFieldName, Long.class));
		assertEquals((long) 1, this.toTest.provide(randomFieldName, Long.class));
	}

	@Test
	public void testThatIncreaseWorksForEvenSteps() {
		//when
		this.toTest.increase(10);

		//then
		assertEquals((long) 10, this.toTest.provide(randomFieldName, Long.class));
		assertEquals((long) 11, this.toTest.provide(randomFieldName, Long.class));
	}

	@Test
	public void testThatIncreaseWorksForOddSteps() {
		//when
		this.toTest.increase(9);

		//then
		assertEquals((long) 9, this.toTest.provide(randomFieldName, Long.class));
		assertEquals((long) 10, this.toTest.provide(randomFieldName, Long.class));
	}

	@Test
	public void testThatResetWorks() {
		//given
		assertEquals((long) 0, this.toTest.provide(randomFieldName, Long.class));
		assertEquals((long) 1, this.toTest.provide(randomFieldName, Long.class));

		//when
		this.toTest.reset();

		//then
		assertEquals((long) 0, this.toTest.provide(randomFieldName, Long.class));
	}
}
