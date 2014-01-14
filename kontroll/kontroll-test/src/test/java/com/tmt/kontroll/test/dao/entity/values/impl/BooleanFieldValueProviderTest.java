package com.tmt.kontroll.test.dao.entity.values.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProviderTest;

public class BooleanFieldValueProviderTest extends ValueFieldProviderTest {

	public BooleanFieldValueProviderTest() {
		super(new BooleanFieldValueProvider(), String.class, "blubb");
	}

	@Test
	public void testThatValueProviderIsInitializedCorrectly() {
		assertFalse((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
	}

	@Test
	public void testThatProvisionAndIncrementWorks() {
		assertFalse((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
		assertTrue((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueTrue() {
		//when
		this.toTest.offset(randomFieldName, Boolean.TRUE);

		//then
		assertFalse((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
		assertTrue((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
	}

	@Test
	public void testThatOffsetWorksForOffsetValueFalse() {
		//when
		this.toTest.offset(randomFieldName, Boolean.FALSE);

		//then
		assertFalse((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
		assertFalse((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
	}

	@Test
	public void testThatIncreaseWorksForZeroSteps() {
		//when
		this.toTest.increase(0);

		//then
		assertFalse((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
		assertTrue((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
	}

	@Test
	public void testThatIncreaseWorksForEvenSteps() {
		//when
		this.toTest.increase(10);

		//then
		assertFalse((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
		assertTrue((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
	}

	@Test
	public void testThatIncreaseWorksForOddSteps() {
		//when
		this.toTest.increase(9);

		//then
		assertTrue((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
		assertFalse((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
	}

	@Test
	public void testThatResetWorks() {
		//given
		assertFalse((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
		assertTrue((Boolean) this.toTest.provide(randomFieldName, Boolean.class));

		//when
		this.toTest.reset();

		//then
		assertFalse((Boolean) this.toTest.provide(randomFieldName, Boolean.class));
	}
}
