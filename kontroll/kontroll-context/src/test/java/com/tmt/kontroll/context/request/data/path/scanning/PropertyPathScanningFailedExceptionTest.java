package com.tmt.kontroll.context.request.data.path.scanning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.tmt.kontroll.test.ExceptionTest;

public class PropertyPathScanningFailedExceptionTest extends ExceptionTest {

	private static final String path = "path";
	private static final String propertyName = "propertyName";

	@SuppressWarnings("serial")
	public PropertyPathScanningFailedExceptionTest() {
		super(new ArrayList<String>(){{
			add(PropertyPathScanningFailedExceptionTest.class.getPackage().getName());
		}});
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void testThatPrepareWorks() {
		//when
		final PropertyPathScanningFailedException exception = PropertyPathScanningFailedException.prepare(new RuntimeException(), propertyName, path);
		
		//then
		assertFalse(exception.getContextEntries().isEmpty());
		assertEquals(path, exception.getContextValues(path).get(0));
		assertEquals(propertyName, exception.getContextValues(propertyName).get(0));
	}
}
