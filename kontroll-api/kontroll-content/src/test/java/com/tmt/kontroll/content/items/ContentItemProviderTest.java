package com.tmt.kontroll.content.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.config.BeanDefinition;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.commons.utils.AnnotationAndAssignableTypeCandidateScanner;
import com.tmt.kontroll.content.config.ContentProperties;
import com.tmt.kontroll.content.exceptions.NoContentItemFoundException;
import com.tmt.kontroll.content.persistence.selections.HtmlTag;

@RunWith(MockitoJUnitRunner.class)
public class ContentItemProviderTest {
	
	private static final String contentItemKeyReference = HtmlTag.class.getName() + "." + HtmlTag.Anchor.name();

	public static class InitializableTestContentItem extends ContentItem<HtmlTag> {

		public static final HtmlTag tag = HtmlTag.Anchor;
		@Override
		public HtmlTag getTag() {
			return tag;
		}
	}
	
	private static class NotInitializableTestContentItem extends ContentItem<HtmlTag> {

		public static final HtmlTag tag = HtmlTag.Anchor;
		@Override
		public HtmlTag getTag() {
			return tag;
		}
	}
	
	@Mock
	private AnnotationAndAssignableTypeCandidateScanner candidateScanner;
	@Mock
	private Map<String, Class<? extends ContentItem<?>>> contentItemMap;
	@Mock
	private ContentProperties contentProperties;
	@Mock
	private BeanDefinition beanDefinition;

	private ContentItemProvider toTest;
	
	@Before
	@SuppressWarnings({"unchecked", "serial"})
	public void setUp() throws Exception {
		initMocks(this);
		when(this.candidateScanner.scan(eq(Content.class), eq(ContentItem.class), any(List.class))).thenReturn(new HashSet<BeanDefinition>(){{
			add(beanDefinition);
		}});
		when(this.contentProperties.getContentItemBasePackages()).thenReturn(new ArrayList<String>());
		this.toTest = new ContentItemProvider();
		this.toTest.candidateScanner = this.candidateScanner;
		this.toTest.contentItemMap = this.contentItemMap;
		this.toTest.contentProperties = this.contentProperties;
	}

	@Test
	public void testThatContentItemMapIsGeneratedProperlyWithExistingBeanDefinition() throws Exception {
		//given
		when(this.beanDefinition.getBeanClassName()).thenReturn(InitializableTestContentItem.class.getName());

		//when
		this.toTest.generateContentItemMap();
		
		//then
		verify(this.contentItemMap).put(contentItemKeyReference, InitializableTestContentItem.class);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testThatContentItemMapIsGeneratedProperlyWithoutAnyBeanDefinition() throws Exception {
		//given
		when(this.candidateScanner.scan(eq(Content.class), eq(ContentItem.class), any(List.class))).thenReturn(new HashSet<BeanDefinition>());
		
		//when
		this.toTest.generateContentItemMap();
		
		//then
		verify(this.beanDefinition, never()).getBeanClassName();
		verify(this.contentItemMap, never()).put(any(String.class), any(Class.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = RuntimeException.class)
	public void testThatContentItemMapGenerationThrowsClassNotFoundException() {
		//given
		when(this.beanDefinition.getBeanClassName()).thenThrow(RuntimeException.class);

		//when
		this.toTest.generateContentItemMap();
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testThatProvisionWorksForExistingContentItem() throws Exception {
		//given
		this.toTest.contentItemMap = new HashMap<String, Class<? extends ContentItem<?>>>(){{
			put(contentItemKeyReference, InitializableTestContentItem.class);
		}};
		
		//when
		ContentItem<?> provided = this.toTest.provide(HtmlTag.Anchor);
		
		//then
		assertNotNull(provided);
		assertEquals(InitializableTestContentItem.class, provided.getClass());
	}
	
	@Test(expected = NoContentItemFoundException.class)
	public void testThatProvisionWillThrowNoContentItemFoundExceptionIfNoContentItemExists() throws Exception {
		//given
		this.toTest.contentItemMap = new HashMap<String, Class<? extends ContentItem<?>>>();
		
		//when
		this.toTest.provide(HtmlTag.Anchor);		
	}
	
	@SuppressWarnings("serial")
	@Test(expected = RuntimeException.class)
	public void testThatProvisionWillThrowRuntimeExceptionIfContentItemInitializationFails() throws Exception {
		//given
		this.toTest.contentItemMap = new HashMap<String, Class<? extends ContentItem<?>>>(){{
			put(contentItemKeyReference, NotInitializableTestContentItem.class);
		}};
		
		//when
		this.toTest.provide(HtmlTag.Anchor);		
	}
}
