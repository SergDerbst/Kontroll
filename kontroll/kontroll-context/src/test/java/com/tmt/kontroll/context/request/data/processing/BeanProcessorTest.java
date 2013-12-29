package com.tmt.kontroll.context.request.data.processing;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.beans.PropertyDescriptor;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.BeanUtils;

import com.tmt.kontroll.commons.exceptions.ScanFailedException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BeanUtils.class)
public class BeanProcessorTest {
	
	private BeanProcessor toTest;
	
	@Mock
	private PropertyProcessingChain processingChain;
	@Mock
	private PropertyDescriptor propertyDescriptor;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(BeanUtils.class);
		this.toTest = new BeanProcessor();
		this.toTest.processingChain = this.processingChain;
	}
	
	@Test
	public void testThatProcessingChainIsCalled() throws Exception {
		//given
		final PropertyDescriptor[] propertyDescriptors = {this.propertyDescriptor};
		when(BeanUtils.getPropertyDescriptors(any(Class.class))).thenReturn(propertyDescriptors);

		//when
		this.toTest.process(new TreeSet<String>(), "path", this.getClass());
		
		//then
		verify(this.processingChain).process("path", this.propertyDescriptor, this.getClass());
	}
	
	@Test(expected = ScanFailedException.class)
	public void testThatExceptionIsThrown() throws Exception {
		//given
		when(BeanUtils.getPropertyDescriptors(any(Class.class))).thenThrow(new RuntimeException());
		
		//when
		this.toTest.process(new TreeSet<String>(), "path", this.getClass());
	}
}
