package com.tmt.kontroll.context.request;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.commons.exceptions.ScanFailedException;
import com.tmt.kontroll.context.request.RequestContextTestHelper.TestRequestContextService;
import com.tmt.kontroll.context.request.data.processing.BeanProcessor;

@RunWith(MockitoJUnitRunner.class)
public class RequestContextDtoPathScannerTest {
	
	@Mock
	private BeanProcessor processor;

	private RequestContextDtoPathScanner toTest;

	@Before
	public void setUp() throws Exception {
		this.toTest = new RequestContextDtoPathScanner();
		this.toTest.processor = this.processor;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testThatScanWorks() throws Exception {
		this.toTest.scan(new TestRequestContextService());
		//then
		verify(this.processor).process(any(SortedSet.class), any(String.class), any(Class.class));
	}
	
	@Test (expected = ScanFailedException.class)
	@SuppressWarnings("unchecked")
	public void testScan() throws Exception {
		//when
		when(this.processor.process(any(SortedSet.class), any(String.class), any(Class.class))).thenThrow(new RuntimeException());
		
		//given
		
		//when
		this.toTest.scan(new TestRequestContextService());
	}

}
