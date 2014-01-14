package com.tmt.kontroll.test.dao.entity.values.impl;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProviderTest;

public class ByteFieldValueProviderTest extends ValueFieldProviderTest {

	public ByteFieldValueProviderTest() {
		super(new ByteFieldValueProvider(), String.class, "blubb");
	}

	@Test
	public void testThatValueProviderIsInitializedCorrectly() {
		assertEquals(Byte.parseByte("0"), this.toTest.provide(randomFieldName, Byte.class));
	}

	@Test
	public void testThatProvisionAndIncrementWorks() {
		assertEquals(Byte.parseByte("0"), this.toTest.provide(randomFieldName, Byte.class));
		assertEquals(Byte.parseByte("1"), this.toTest.provide(randomFieldName, Byte.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueOne() {
		//when
		this.toTest.offset(randomFieldName, Byte.parseByte("1"));

		//then
		assertEquals(Byte.parseByte("1"), this.toTest.provide(randomFieldName, Byte.class));
		assertEquals(Byte.parseByte("2"), this.toTest.provide(randomFieldName, Byte.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueZero() {
		//when
		this.toTest.offset(randomFieldName, Byte.parseByte("0"));

		//then
		assertEquals(Byte.parseByte("0"), this.toTest.provide(randomFieldName, Byte.class));
		assertEquals(Byte.parseByte("1"), this.toTest.provide(randomFieldName, Byte.class));
	}

	@Test
	public void testThatIncreaseWorksForZeroSteps() {
		//when
		this.toTest.increase(0);

		//then
		assertEquals(Byte.parseByte("0"), this.toTest.provide(randomFieldName, Byte.class));
		assertEquals(Byte.parseByte("1"), this.toTest.provide(randomFieldName, Byte.class));
	}

	@Test
	public void testThatIncreaseWorksForEvenSteps() {
		//when
		this.toTest.increase(10);

		//then
		assertEquals(Byte.parseByte("10"), this.toTest.provide(randomFieldName, Byte.class));
		assertEquals(Byte.parseByte("11"), this.toTest.provide(randomFieldName, Byte.class));
	}

	@Test
	public void testThatIncreaseWorksForOddSteps() {
		//when
		this.toTest.increase(9);

		//then
		assertEquals(Byte.parseByte("9"), this.toTest.provide(randomFieldName, Byte.class));
		assertEquals(Byte.parseByte("10"), this.toTest.provide(randomFieldName, Byte.class));
	}

	@Test
	public void testThatResetWorks() {
		//given
		assertEquals(Byte.parseByte("0"), this.toTest.provide(randomFieldName, Byte.class));
		assertEquals(Byte.parseByte("1"), this.toTest.provide(randomFieldName, Byte.class));

		//when
		this.toTest.reset();

		//then
		assertEquals(Byte.parseByte("0"), this.toTest.provide(randomFieldName, Byte.class));
	}
}
