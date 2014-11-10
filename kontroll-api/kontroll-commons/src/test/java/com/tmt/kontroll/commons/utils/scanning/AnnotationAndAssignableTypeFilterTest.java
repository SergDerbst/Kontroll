package com.tmt.kontroll.commons.utils.scanning;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.commons.utils.scanning.AnnotationAndAssignableTypeFilter;

public class AnnotationAndAssignableTypeFilterTest {
	
	@SuppressWarnings("serial")
	private static class AssignableClass implements Serializable {}
	
	private static class NotAssignableClass {}

	@Mock
	private ClassMetadata classMetadata;
	@Mock
	private MetadataReader metadataReader;
	@Mock
	private AnnotationMetadata annotationMetadata;
	
	private AnnotationAndAssignableTypeFilter toTest;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		when(this.metadataReader.getClassMetadata()).thenReturn(this.classMetadata);
		when(this.metadataReader.getAnnotationMetadata()).thenReturn(this.annotationMetadata);
	}

	@Test
	public void testThatAnnotationAndAssignableTypeMatch() throws Exception {
		//given
		when(this.classMetadata.getClassName()).thenReturn(AssignableClass.class.getName());
		when(this.annotationMetadata.hasAnnotation(any(String.class))).thenReturn(true);
		this.toTest = new AnnotationAndAssignableTypeFilter(Component.class, Serializable.class);
		
		//when
		final boolean match = this.toTest.matchSelf(this.metadataReader);
		
		//then
		assertTrue(match);
	}
	
	@Test
	public void testThatAnnotationMatchesButAssignableTypeDoesNot() throws Exception {
		//given
		when(this.classMetadata.getClassName()).thenReturn(NotAssignableClass.class.getName());
		when(this.annotationMetadata.hasAnnotation(any(String.class))).thenReturn(true);
		this.toTest = new AnnotationAndAssignableTypeFilter(Component.class, Serializable.class);
		
		//when
		final boolean match = this.toTest.matchSelf(this.metadataReader);
		
		//then
		assertFalse(match);
	}
	
	@Test
	public void testThatAnnotationDoesNotMatchButAssignableTypeDoes() throws Exception {
		//given
		when(this.classMetadata.getClassName()).thenReturn(AssignableClass.class.getName());
		when(this.annotationMetadata.hasAnnotation(any(String.class))).thenReturn(false);
		this.toTest = new AnnotationAndAssignableTypeFilter(Component.class, Serializable.class);
		
		//when
		final boolean match = this.toTest.matchSelf(this.metadataReader);
		
		//then
		assertFalse(match);
	}
	
	@Test
	public void testThatAnnotationAndAssignableTypeDoNotMatch() throws Exception {
		//given
		when(this.classMetadata.getClassName()).thenReturn(NotAssignableClass.class.getName());
		when(this.annotationMetadata.hasAnnotation(any(String.class))).thenReturn(false);
		this.toTest = new AnnotationAndAssignableTypeFilter(Component.class, Serializable.class);
		
		//when
		final boolean match = this.toTest.matchSelf(this.metadataReader);
		
		//then
		assertFalse(match);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = RuntimeException.class)
	public void testThatExceptionIsThrown() {
		//given
		when(this.annotationMetadata.hasAnnotation(any(String.class))).thenThrow(ClassNotFoundException.class);
		this.toTest = new AnnotationAndAssignableTypeFilter(Component.class, Serializable.class);
		
		//when
		final boolean match = this.toTest.matchSelf(this.metadataReader);
		
		//then
		assertFalse(match);
	}
}
