package com.tmt.kontroll.context.global;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.commons.utils.RegexPatternConverter;
import com.tmt.kontroll.context.request.RequestContextItem;

public class GlobalContextTest {
	
	private static final String regex = "regex";

	private static final Pattern pattern = Pattern.compile(regex);
	
	@Mock
	private TreeMap<Pattern, Set<RequestContextItem>> requestContextMap;
	@Mock
	private RegexPatternConverter regexPatternConverter;
	@Mock
	private GlobalContextDto globalData;
	@Mock
	private RequestContextItem requestContextItem;
	@Mock
	private Set<RequestContextItem> set;
	@Mock
	private NavigableMap<Pattern, Set<RequestContextItem>> descendingMap;
	@Mock
	private Entry<Pattern, Set<RequestContextItem>> entry;
	
	private GlobalContext toTest;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(Pattern.class);
		this.toTest = new GlobalContext();
		this.toTest.globalData = this.globalData;
		this.toTest.regexPatternConverter = this.regexPatternConverter;
		this.toTest.requestContextMap = requestContextMap;
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testThatAddingRequestContextWorksProperlyForNewRequestContext() {
		//given
		when(this.regexPatternConverter.convertToPattern(any(String.class))).thenReturn(pattern);
		when(this.requestContextMap.get(any(Pattern.class))).thenReturn(null);
		
		//when
		this.toTest.addRequestContextItem(regex, this.requestContextItem);
		
		//then
		verify(this.requestContextMap).put(eq(pattern), any(Set.class));
	}
	
	@Test
	public void testThatAddingRequestContextWorksProperlyForExistingRequestContext() {
		//given
		when(this.regexPatternConverter.convertToPattern(any(String.class))).thenReturn(pattern);
		when(this.requestContextMap.get(any(Pattern.class))).thenReturn(this.set);
		
		//when
		this.toTest.addRequestContextItem(regex, this.requestContextItem);
		
		//then
		verify(this.set).add(this.requestContextItem);
	}
	
	@Test
	public void testThatFetchRequestContextWorksProperlyForPattern() {
		//when
		this.toTest.fetchRequestContext(pattern);
		
		//then
		verify(this.requestContextMap).get(pattern);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testThatFetchRequestContextWorksProperlyForMatchingRequestContextPath() {
		//given
		when(this.entry.getKey()).thenReturn(pattern);
		when(this.descendingMap.entrySet()).thenReturn(new HashSet<Entry<Pattern, Set<RequestContextItem>>>(){{
			add(entry);
		}});
		when(this.requestContextMap.descendingMap()).thenReturn(descendingMap);
		
		//when
		Set<RequestContextItem> fetched = this.toTest.fetchRequestContext(regex);
		
		//then
		verify(this.entry).getValue();
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testThatFetchRequestContextWorksProperlyForNotMatchingRequestContextPath() {
		//given
		when(this.entry.getKey()).thenReturn(pattern);
		when(this.descendingMap.entrySet()).thenReturn(new HashSet<Entry<Pattern, Set<RequestContextItem>>>(){{
			add(entry);
		}});
		when(this.requestContextMap.descendingMap()).thenReturn(descendingMap);
		
		//when
		Set<RequestContextItem> fetched = this.toTest.fetchRequestContext("wurst");
		
		//then
		assertNull(fetched);
		verify(this.entry, never()).getValue();
	}
	
	@Test
	public void testThatFetchRequestContextWorksProperlyForNonExistingRequestContexts() {
		//given
		when(this.entry.getKey()).thenReturn(pattern);
		when(this.descendingMap.entrySet()).thenReturn(new HashSet<Entry<Pattern, Set<RequestContextItem>>>());
		when(this.requestContextMap.descendingMap()).thenReturn(descendingMap);
		
		//when
		Set<RequestContextItem> fetched = this.toTest.fetchRequestContext("wurst");
		
		//then
		assertNull(fetched);
		verify(this.entry, never()).getValue();
	}
	
	@Test
	public void testThatGetterAndSetterWork() {
		this.toTest.setGlobalContextDto(this.globalData);
		assertEquals(this.globalData, this.toTest.getGlobalContextDto());
	}
}
