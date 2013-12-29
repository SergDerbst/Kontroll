package com.tmt.kontroll.context.request.data.processing.impl;


import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.beans.PropertyDescriptor;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tmt.kontroll.context.request.RequestContextTestHelper.TestRequestContextDto;

public class TerminalPropertyProcessorTest {
	
	private PropertyDescriptor propertyDescriptor;
	private TerminalPropertyProcessor toTest;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.propertyDescriptor = new PropertyDescriptor("string", TestRequestContextDto.class);
		this.toTest = new TerminalPropertyProcessor();
	}

	@Test
	public void testProcess() throws Exception {
			//given
		
			//when
			final Set<String> set = this.toTest.process("path", this.propertyDescriptor, TestRequestContextDto.class);
			
			//then
			assertTrue(set.contains("path.string"));
	}
}
