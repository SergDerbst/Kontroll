package com.tmt.kontroll.content;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.content.exceptions.NoContentFoundException;
import com.tmt.kontroll.content.exceptions.NoScopeFoundException;
import com.tmt.kontroll.content.parsers.ContentParser;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.services.ScopeDaoService;
import com.tmt.kontroll.content.persistence.services.ScopedContentDaoService;
import com.tmt.kontroll.content.verification.ContentVerifier;

@RunWith(MockitoJUnitRunner.class)
public class ContentServiceTest {
	
	private static final String scopeName = "some.scopeName";
	
	@Mock
	private ScopeDaoService scopeDaoService;
	@Mock
	private ScopedContentDaoService scopedContentDaoService;
	@Mock
	private ContentParser scopedContentParser;
	@Mock
	private ContentVerifier verifier;
	@Mock
	private ContentDto contentDTO;
	@Mock
	private Scope scope;
	@Mock
	private ScopedContent scopedContent;
	
	private ContentService toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		when(this.contentDTO.getScopeName()).thenReturn(scopeName);
		this.toTest = new ContentService();
		this.toTest.scopeDaoService = this.scopeDaoService;
		this.toTest.scopedContentDaoService = this.scopedContentDaoService;
		this.toTest.scopedContentParser = this.scopedContentParser;
		this.toTest.verifier = this.verifier;
	}

	@Test
	@SuppressWarnings("serial")
	public void testThatContentIsLoaded() throws Exception {
		//given
		when(this.scopeDaoService.findByName(any(String.class))).thenReturn(this.scope);
		when(this.scopedContentDaoService.findByScopeAndName(any(Scope.class), any(String.class))).thenReturn(new ArrayList<ScopedContent>(){{
			add(scopedContent);
		}});
		when(this.scopedContent.getConditions()).thenReturn(new ArrayList<ScopedContentCondition>());
		when(this.verifier.verify(any(ContentContext.class))).thenReturn(true);
		
		//when
		this.toTest.loadContent(this.contentDTO);
		
		//then
		verify(this.scopedContentParser).parse(this.scopedContent);
	}
	
	@Test(expected = NoContentFoundException.class)
	@SuppressWarnings("serial")
	public void testThatNoContentFoundExceptionIsThrownIfNoContextCouldBeVerified() throws Exception {
		//given
		when(this.scopeDaoService.findByName(any(String.class))).thenReturn(this.scope);
		when(this.scopedContentDaoService.findByScopeAndName(any(Scope.class), any(String.class))).thenReturn(new ArrayList<ScopedContent>(){{
			add(scopedContent);
		}});
		when(this.scopedContent.getConditions()).thenReturn(new ArrayList<ScopedContentCondition>());
		when(this.verifier.verify(any(ContentContext.class))).thenReturn(false);
		
		//when
		this.toTest.loadContent(this.contentDTO);
	}
	
	@Test(expected = NoContentFoundException.class)
	public void testThatNoContentFoundExceptionIsThrownForScopeWithoutContent() throws Exception {
		//given
		when(this.scopeDaoService.findByName(any(String.class))).thenReturn(this.scope);
		when(this.scopedContentDaoService.findByScopeAndName(any(Scope.class), any(String.class))).thenReturn(new ArrayList<ScopedContent>());
		
		//when
		this.toTest.loadContent(this.contentDTO);
	}
	
	@Test(expected = NoScopeFoundException.class)
	public void testThatNoScopeFoundExceptionIsThrownForNonExistingScope() throws Exception {
		//given
		when(this.scopeDaoService.findByName(any(String.class))).thenReturn(null);
		
		//when
		this.toTest.loadContent(this.contentDTO);
	}
}
