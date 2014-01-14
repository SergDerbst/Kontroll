package com.tmt.kontroll.test.dao.entity.values.impl;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProviderTest;


public class TimestampFieldValueProviderTest extends ValueFieldProviderTest {

	private long firstProvision;

	public TimestampFieldValueProviderTest() {
		super(new TimestampFieldValueProvider(), String.class, "blubb");
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		this.firstProvision = ((Timestamp) this.toTest.provide(randomFieldName, Timestamp.class)).getTime();
	}

	@Test
	public void testThatFieldValueProviderIsInitializedCorrectly() {
		assertEquals(this.firstProvision + 1, ((Timestamp) this.toTest.provide(randomFieldName, Timestamp.class)).getTime());
	}

	@Test
	public void testThatProvisionAndIncrementWorks() {
		assertEquals(this.firstProvision + 1, ((Timestamp) this.toTest.provide(randomFieldName, Timestamp.class)).getTime());
		assertEquals(this.firstProvision + 2, ((Timestamp) this.toTest.provide(randomFieldName, Timestamp.class)).getTime());
	}

	@Test
	public void testThatOffsetWorksForOffsetValue() {
		//given
		final long offsetValue = System.currentTimeMillis();

		//when
		this.toTest.offset(randomFieldName, new Timestamp(offsetValue));

		//then
		assertEquals(this.firstProvision + offsetValue + 1, ((Timestamp) this.toTest.provide(randomFieldName, Timestamp.class)).getTime());
	}

	@Test
	public void testThatIncreaseWorksForZeroSteps() {
		//when
		this.toTest.increase(0);

		//then
		assertEquals(this.firstProvision + 1, ((Timestamp) this.toTest.provide(randomFieldName, Timestamp.class)).getTime());
	}

	@Test
	public void testThatIncreaseWorksForEvenSteps() {
		//when
		this.toTest.increase(10);

		//then
		assertEquals(this.firstProvision + 11, ((Timestamp) this.toTest.provide(randomFieldName, Timestamp.class)).getTime());
	}

	@Test
	public void testThatIncreaseWorksForOddSteps() {
		//when
		this.toTest.increase(9);

		//then
		assertEquals(this.firstProvision + 10, ((Timestamp) this.toTest.provide(randomFieldName, Timestamp.class)).getTime());
	}

	@Test
	public void testThatResetWorks() {
		//given
		assertEquals(this.firstProvision + 1, ((Timestamp) this.toTest.provide(randomFieldName, Timestamp.class)).getTime());
		assertEquals(this.firstProvision + 2, ((Timestamp) this.toTest.provide(randomFieldName, Timestamp.class)).getTime());

		//when
		this.toTest.reset();

		//then
		assertEquals(this.firstProvision, ((Timestamp) this.toTest.provide(randomFieldName, Timestamp.class)).getTime());
	}
}
