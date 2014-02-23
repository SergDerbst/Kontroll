package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;

import org.junit.Test;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProviderTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class TimestampValueProviderTest extends ValueProviderTest<Timestamp> {

	public TimestampValueProviderTest() throws Exception {
		super(new TimestampValueProvider(ValueProvisionHandler.newInstance()),
		      new Timestamp(System.currentTimeMillis()),
		      new Timestamp(System.currentTimeMillis()),
		      Timestamp.class.getSimpleName(),
		      Dummy.class,
		      Timestamp.class);
	}

	@Override
	@Test
	public void testThatProvideWorks() throws Exception {
		//when
		final Object provided = this.toTest.provide(new Dummy(), this.field, this.types);
		//then
		assertTrue(super.referenceValue.getTime() <= ((Timestamp) provided).getTime());
		verify(super.nextProvider, never()).provide(new Dummy(), this.field, this.types);
	}

	@Override
	@Test
	public void testThatFetchNextValueWorks() throws Exception {
		//when
		final Object fetched = this.toTest.fetchNextValue(new Dummy(), this.field, super.referenceValue);
		//then
		assertTrue(super.referenceValue.getTime() <= ((Timestamp) fetched).getTime());
		verify(this.nextProvider, never()).fetchNextValue(new Dummy(), this.field, super.referenceValue);
	}
}
