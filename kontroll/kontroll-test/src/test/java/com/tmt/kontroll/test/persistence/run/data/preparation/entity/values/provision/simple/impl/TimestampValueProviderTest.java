package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;

import org.junit.Test;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProviderTest;

public class TimestampValueProviderTest extends SimpleValueProviderTest<Timestamp> {

	public TimestampValueProviderTest() throws Exception {
		super(new TimestampValueProvider(ValueProvisionHandler.newInstance()),
		      new Timestamp(System.currentTimeMillis()),
		      new Timestamp(System.currentTimeMillis()),
		      Dummy.class,
		      Timestamp.class);
	}

	@Override
	@Test
	public void testThatProvideWorks() throws Exception {
		//when
		final Object provided = this.toTest.provide(new Dummy(), ValueProvisionKind.ZeroDimensional, this.types);
		//then
		assertTrue(super.referenceValue.getTime() <= ((Timestamp) provided).getTime());
		verify(super.nextProvider, never()).provide(new Dummy(), ValueProvisionKind.ZeroDimensional, this.types);
	}

	@Override
	@Test
	public void testThatFetchNextValueWorks() throws Exception {
		//when
		final Object fetched = this.toTest.fetchNextZeroDimensionalValue(new Dummy(), super.referenceValue);
		//then
		assertTrue(super.referenceValue.getTime() <= ((Timestamp) fetched).getTime());
		verify(this.nextProvider, never()).fetchNextZeroDimensionalValue(new Dummy(), super.referenceValue);
	}
}
