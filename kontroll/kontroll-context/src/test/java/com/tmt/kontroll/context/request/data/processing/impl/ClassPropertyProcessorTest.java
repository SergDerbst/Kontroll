package com.tmt.kontroll.context.request.data.processing.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.beans.PropertyDescriptor;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.context.request.data.processing.PropertyPathProcessingFailedException;
import com.tmt.kontroll.context.request.data.processing.PropertyProcessor;

public class ClassPropertyProcessorTest {

	private ClassPropertyProcessor toTest;
	
	@Mock
	private PropertyProcessor processor;
	
	@Mock
	private PropertyDescriptor propertyDescriptor;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.toTest = new ClassPropertyProcessor();
	}

	@Test
	public void testThatProcessWorksForClassProperty() throws Exception {
		//given
		when(this.propertyDescriptor.getName()).thenReturn("class");
		
		//when
		final Set<String> set = this.toTest.process("path", this.propertyDescriptor, this.getClass());
		
		//then
		assertTrue(set.isEmpty());
	}
	
	@Test
	public void testThatNextProcessorIsCalledForNonClassProperty() throws Exception {
		//given
		when(this.propertyDescriptor.getName()).thenReturn("bongo");
		this.toTest.setNextProcessor(this.processor);
		
		//when
		this.toTest.process("path", this.propertyDescriptor, this.getClass());
		
		//then
		verify(this.processor).process(any(String.class), any(PropertyDescriptor.class), any(Class.class));
	}
	
	@Test(expected = PropertyPathProcessingFailedException.class)
	public void testThatNextProcessorIsCalledForNonClassPropertyAndNoNextProcessor() throws Exception {
		//given
		when(this.propertyDescriptor.getName()).thenReturn("bongo");
		
		//when
		this.toTest.process("path", this.propertyDescriptor, this.getClass());
	}
}
