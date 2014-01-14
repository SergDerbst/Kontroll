package com.tmt.kontroll.test.dao.entity.values.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProviderTest;

public class CharacterFieldValueProviderTest extends ValueFieldProviderTest {

	public CharacterFieldValueProviderTest() {
		super(new CharacterFieldValueProvider(), String.class, "blubb");
	}

	@Test
	public void testThatValueProviderIsInitializedCorrectly() {
		assertEquals("0".charAt(0), this.toTest.provide(randomFieldName, Character.class));
	}

	@Test
	public void testThatProvisionAndIncrementWorks() {
		assertEquals("0".charAt(0), this.toTest.provide(randomFieldName, Character.class));
		assertEquals("1".charAt(0), this.toTest.provide(randomFieldName, Character.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueOne() {
		//when
		this.toTest.offset(randomFieldName, "1".charAt(0));

		//then
		assertEquals("1".charAt(0), this.toTest.provide(randomFieldName, Character.class));
		assertEquals("2".charAt(0), this.toTest.provide(randomFieldName, Character.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueZero() {
		//when
		this.toTest.offset(randomFieldName, "0".charAt(0));

		//then
		assertEquals("0".charAt(0), this.toTest.provide(randomFieldName, Character.class));
		assertEquals("1".charAt(0), this.toTest.provide(randomFieldName, Character.class));
	}

	@Test
	public void testThatIncreaseWorksForZeroSteps() {
		//when
		this.toTest.increase(0);

		//then
		assertEquals("0".charAt(0), this.toTest.provide(randomFieldName, Character.class));
		assertEquals("1".charAt(0), this.toTest.provide(randomFieldName, Character.class));
	}

	@Test
	public void testThatIncreaseWorksForEvenSteps() {
		//when
		this.toTest.increase(10);

		//then
		assertEquals("1".charAt(0), this.toTest.provide(randomFieldName, Character.class));
		assertEquals("2".charAt(0), this.toTest.provide(randomFieldName, Character.class));
	}

	@Test
	public void testThatIncreaseWorksForOddSteps() {
		//when
		this.toTest.increase(9);

		//then
		assertEquals("9".charAt(0), this.toTest.provide(randomFieldName, Character.class));
		assertEquals("1".charAt(0), this.toTest.provide(randomFieldName, Character.class));
	}

	@Test
	public void testThatResetWorks() {
		//given
		assertEquals("0".charAt(0), this.toTest.provide(randomFieldName, Character.class));
		assertEquals("1".charAt(0), this.toTest.provide(randomFieldName, Character.class));

		//when
		this.toTest.reset();

		//then
		assertEquals("0".charAt(0), this.toTest.provide(randomFieldName, Character.class));
	}
}
