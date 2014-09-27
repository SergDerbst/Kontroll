package com.tmt.kontroll.context.request;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.config.BeanDefinition;

import com.tmt.kontroll.annotations.RequestContext;
import com.tmt.kontroll.commons.exceptions.ScanFailedException;
import com.tmt.kontroll.commons.utils.AnnotationAndAssignableTypeCandidateScanner;
import com.tmt.kontroll.context.config.ContextProperties;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.context.request.RequestContextTestHelper.TestRequestContextService;

public class RequestContextScannerTest {
	
	@Mock
	private AnnotationAndAssignableTypeCandidateScanner candidateScanner;
	@Mock
	private ContextProperties contextProperties;
	@Mock
	private RequestContextDtoPathScanner dtoPathScanner;
	@Mock
	private GlobalContext globalContext;
	@Mock
	private BeanDefinition beanDefinition;
	
	private RequestContextScanner toTest;

	@Before
	@SuppressWarnings({"unchecked", "serial"})
	public void setUp() throws Exception {
		initMocks(this);
		when(this.beanDefinition.getBeanClassName()).thenReturn(TestRequestContextService.class.getName());
		when(this.contextProperties.contextServiceBasePackages()).thenReturn(new ArrayList<String>());
		when(this.candidateScanner.scan(eq(RequestContext.class), eq(RequestContextService.class), any(List.class))).thenReturn(new HashSet<BeanDefinition>() {{
			add(beanDefinition);
		}});
		this.toTest = new RequestContextScanner();
		this.toTest.contextProperties = this.contextProperties;
		this.toTest.candidateScanner = this.candidateScanner;
		this.toTest.dtoPathScanner = this.dtoPathScanner;
		this.toTest.globalContext = this.globalContext;
	}

	@Test
	public void testThatScanWorks() throws Exception {
		//when
		this.toTest.scan();
		
		//then
		verify(this.dtoPathScanner).scan(any(TestRequestContextService.class));
		verify(this.globalContext).addRequestContextItem(eq(RequestContextTestHelper.pattern), any(RequestContextItem.class));
	}
	
	@Test(expected = ScanFailedException.class)
	public void testThatScanThrowsScanFailedException() throws Exception {
		//given
		when(this.dtoPathScanner.scan(any(TestRequestContextService.class))).thenThrow(new ScanFailedException(new RuntimeException()));
		//when
		this.toTest.scan();
	}
	
	@Test(expected = ScanFailedException.class)
	public void testThatScanCapturesOtherExceptionsAsScanFailedException() throws Exception {
		//given
		when(this.dtoPathScanner.scan(any(TestRequestContextService.class))).thenThrow(new RuntimeException());
		//when
		this.toTest.scan();
	}
}
