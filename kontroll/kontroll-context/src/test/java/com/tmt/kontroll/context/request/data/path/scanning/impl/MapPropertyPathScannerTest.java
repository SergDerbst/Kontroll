package com.tmt.kontroll.context.request.data.path.scanning.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.beans.PropertyDescriptor;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.context.request.RequestContextTestHelper.TestRequestContextDto;
import com.tmt.kontroll.context.request.data.path.scanning.BeanPathScanner;
import com.tmt.kontroll.context.request.data.path.scanning.PropertyPathScanningFailedException;

public class MapPropertyPathScannerTest {

	@Mock
	private BeanPathScanner beanProcessor;

	private PropertyDescriptor propertyDescriptor;
	private MapPropertyPathScanner toTest;
	@SuppressWarnings("serial")
	private final SortedSet<String> keyPaths = new TreeSet<String>() {{
		add("path.key");
	}};
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.propertyDescriptor = new PropertyDescriptor("map", TestRequestContextDto.class);
		this.toTest = new MapPropertyPathScanner();
		this.toTest.beanProcessor = this.beanProcessor;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testThatProcessWorksForMapProperty() throws Exception {
		//given
		when(this.beanProcessor.process(any(SortedSet.class), eq("path.key"), any(Class.class))).thenReturn(this.keyPaths);
		
		//when
		this.toTest.process("path", this.propertyDescriptor, TestRequestContextDto.class);
		
		//then
		verify(this.beanProcessor).process(any(SortedSet.class), eq("path.key"), eq(String.class));
		verify(this.beanProcessor).process(any(SortedSet.class), eq("path.key.value"), eq(Integer.class));
	}
	
	@Test(expected = PropertyPathScanningFailedException.class)
	@SuppressWarnings("unchecked")
	public void testThatProcessThrowsException() throws Exception {
		//given
		when(this.beanProcessor.process(any(SortedSet.class), eq("path.key"), any(Class.class))).thenThrow(new RuntimeException());
		
		//when
		this.toTest.process("path", this.propertyDescriptor, TestRequestContextDto.class);
	}
}
