package com.tmt.kontroll.test.dao.entity.values.impl;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProviderTest;


public class LocaleFieldValueProviderTest extends ValueFieldProviderTest {

	public LocaleFieldValueProviderTest() {
		super(new LocaleFieldValueProvider(),
		      String.class,
		      randomFieldName);
	}

	@Test
	public void testThatFieldValueProviderIsInitializedCorrectly() {
		assertEquals("de", this.toTest.provide(randomFieldName, Locale.class).toString());
	}

	@Test
	public void testThatProvisionAndIncrementWorks() {
		assertEquals("de", this.toTest.provide(randomFieldName, Locale.class).toString());
		assertEquals("de_DE", this.toTest.provide(randomFieldName, Locale.class).toString());
	}

	@Test
	public void testThatOffsetWorksForOffsetValue() {
		//when
		this.toTest.offset(randomFieldName, Locale.ENGLISH);

		//then
		assertEquals("en", this.toTest.provide(randomFieldName, Locale.class).toString());
		assertEquals("en_CA", this.toTest.provide(randomFieldName, Locale.class).toString());
	}

	@Test
	public void testThatIncreaseWorksForZeroSteps() {
		//when
		this.toTest.increase(0);

		//then
		assertEquals("de", this.toTest.provide(randomFieldName, Locale.class).toString());
		assertEquals("de_DE", this.toTest.provide(randomFieldName, Locale.class).toString());
	}

	@Test
	public void testThatIncreaseWorksForEvenSteps() {
		//when
		this.toTest.increase(10);

		//then
		assertEquals("it_IT", this.toTest.provide(randomFieldName, Locale.class).toString());
		assertEquals("ja", this.toTest.provide(randomFieldName, Locale.class).toString());
	}

	@Test
	public void testThatIncreaseWorksForOddSteps() {
		//when
		this.toTest.increase(9);

		//then
		assertEquals("it", this.toTest.provide(randomFieldName, Locale.class).toString());
		assertEquals("it_IT", this.toTest.provide(randomFieldName, Locale.class).toString());
	}

	@Test
	public void testThatIncreaseWorksForLotsOfSteps() {
		//when
		this.toTest.increase(73);

		//then
		assertEquals("en_US", this.toTest.provide(randomFieldName, Locale.class).toString());
		assertEquals("fr", this.toTest.provide(randomFieldName, Locale.class).toString());
	}

	@Test
	public void testThatResetWorks() {
		//given
		assertEquals("de", this.toTest.provide(randomFieldName, Locale.class).toString());
		assertEquals("de_DE", this.toTest.provide(randomFieldName, Locale.class).toString());

		//when
		this.toTest.reset();

		//then
		assertEquals("de", this.toTest.provide(randomFieldName, Locale.class).toString());
	}
}
