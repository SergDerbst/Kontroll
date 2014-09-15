package com.tmt.kontroll.commons.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.stereotype.Component;


public class AnnotationAndAssignableTypeCandidateScannerTest {

	private static final String Boom_Boom = "boom.boom";
	private static final String Bla_Bla = "bla.bla";
	@SuppressWarnings("serial")
	private static final List<String> Boom_Boom_And_Bla_Bla = new ArrayList<String>() {{
		add(Boom_Boom);
		add(Bla_Bla);
	}};

	@Mock
	private ClassPathScanningCandidateComponentProvider scanner;
	
	private AnnotationAndAssignableTypeCandidateScanner toTest;
	
	private final Class<? extends Annotation> annotationType = Component.class;
	private final Class<?> assignableType = Serializable.class;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.toTest = new AnnotationAndAssignableTypeCandidateScanner();
		this.toTest.scanner = this.scanner;
	}

	@Test
	public void testScan() throws Exception {
		//given
		when(this.scanner.findCandidateComponents(any(String.class))).thenReturn(new HashSet<BeanDefinition>());
		
		//when
		final Set<BeanDefinition> scanned = this.toTest.scan(annotationType, assignableType, Boom_Boom_And_Bla_Bla);
		
		//then
		assertNotNull(scanned);
		assertTrue(scanned.isEmpty());
		verify(this.scanner).resetFilters(false);
		verify(this.scanner).addIncludeFilter(any(AnnotationAndAssignableTypeFilter.class));
		verify(this.scanner, times(2)).findCandidateComponents(any(String.class));		
	}
}
