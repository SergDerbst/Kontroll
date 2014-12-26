package com.tmt.kontroll.content.exceptions;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.content.business.content.data.ContentOperatingContext;
import com.tmt.kontroll.test.ExceptionTest;

public class TooMuchContentFoundExceptionTest extends ExceptionTest {

	@Mock
	private ContentOperatingContext contentDto;

	@SuppressWarnings("serial")
	public TooMuchContentFoundExceptionTest() {
		super(new ArrayList<String>(){{
			this.add(this.getClass().getPackage().getName());
		}});
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		initMocks(this);
	}

	@Test
	public void testThatExceptionIsPrepared() {
		final TooMuchContentFoundException prepared = TooMuchContentFoundException.prepare(this.contentDto);
		assertEquals(this.contentDto, prepared.getContextValues("contentDto").get(0));
	}
}
