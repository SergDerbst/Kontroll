package com.tmt.kontroll.context.request.data.processing.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.beans.PropertyDescriptor;
import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.context.request.RequestContextTestHelper.TestRequestContextDto;
import com.tmt.kontroll.context.request.data.processing.BeanProcessor;
import com.tmt.kontroll.context.request.data.processing.PropertyPathProcessingFailedException;

@RunWith(MockitoJUnitRunner.class)
public class CollectionPropertyProcessorTest {
	
	@Mock
	private BeanProcessor beanProcessor;
	
	private PropertyDescriptor propertyDescriptor;
	private CollectionPropertyProcessor toTest;

	@Before
	public void setUp() throws Exception {
		this.propertyDescriptor = new PropertyDescriptor("list", TestRequestContextDto.class);
		this.toTest = new CollectionPropertyProcessor();
		this.toTest.beanProcessor = this.beanProcessor;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testThatProcessWorksForCollectionProperty() throws Exception {
		//when
		this.toTest.process("path", this.propertyDescriptor, TestRequestContextDto.class);
		
		//then
		verify(this.beanProcessor).process(any(SortedSet.class), eq("path.collection"), any(Class.class));
	}
	
	@Test(expected = PropertyPathProcessingFailedException.class)
	@SuppressWarnings("unchecked")
	public void testThatProcessThrowsException() throws Exception {
		//given
		when(this.beanProcessor.process(any(SortedSet.class), eq("path.collection"), any(Class.class))).thenThrow(new RuntimeException());
		
		//when
		this.toTest.process("path", this.propertyDescriptor, TestRequestContextDto.class);
	}
}
