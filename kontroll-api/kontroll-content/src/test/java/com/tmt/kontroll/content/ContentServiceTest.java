package com.tmt.kontroll.content;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.content.exceptions.NoContentFoundException;
import com.tmt.kontroll.content.exceptions.NoScopeFoundException;
import com.tmt.kontroll.content.exceptions.TooMuchContentFoundException;
import com.tmt.kontroll.content.parsers.ContentParser;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.content.persistence.services.impl.ScopeDaoServiceImpl;
import com.tmt.kontroll.content.persistence.services.impl.ScopedContentDaoServiceImpl;
import com.tmt.kontroll.content.verification.ContentConditionVerifier;
import com.tmt.kontroll.test.matchers.EmptyListMatcher;

@RunWith(MockitoJUnitRunner.class)
public class ContentServiceTest {

	@Mock
	private ScopeDaoServiceImpl scopeDaoService;
	@Mock
	private ScopedContentDaoServiceImpl scopedContentDaoService;
	@Mock
	private ContentParser scopedContentParser;
	@Mock
	private ContentConditionVerifier verifier;
	@Mock
	private ContentDto contentDto;
	@Mock
	private Scope scope;
	@Mock
	private ScopedContent scopedContent;
	@Mock
	private ScopedContentCondition scopedContentConditionTrue;
	@Mock
	private ScopedContentCondition scopedContentConditionFalse;
	@Mock
	private ScopedContentItem scopedContentItem;

	private ContentService toTest;

	private List<ScopedContent> contentsReturnedByService;
	private List<ScopedContentItem> contentItems;
	private List<ScopedContentCondition> trueConditionsOfContent;
	private List<ScopedContentCondition> falseConditionsOfContent;

	@Before
	@SuppressWarnings("serial")
	public void setUp() throws Exception {
		this.toTest = new ContentService();
		this.toTest.scopeDaoService = this.scopeDaoService;
		this.toTest.scopedContentDaoService = this.scopedContentDaoService;
		this.toTest.scopedContentParser = this.scopedContentParser;
		this.toTest.verifier = this.verifier;

		this.contentsReturnedByService = new ArrayList<ScopedContent>(){{
			this.add(ContentServiceTest.this.scopedContent);
		}};
		this.contentItems = new ArrayList<ScopedContentItem>(){{
			this.add(ContentServiceTest.this.scopedContentItem);
		}};
		this.trueConditionsOfContent = new ArrayList<ScopedContentCondition>(){{
			this.add(ContentServiceTest.this.scopedContentConditionTrue);
		}};
		this.falseConditionsOfContent = new ArrayList<ScopedContentCondition>(){{
			this.add(ContentServiceTest.this.scopedContentConditionFalse);
		}};

		when(this.contentDto.getScopeName()).thenReturn("scopeName");
		when(this.contentDto.getRequestContextPath()).thenReturn("requestContextPath");
		when(this.verifier.verify(this.scopedContentConditionTrue, this.contentDto)).thenReturn(true);
		when(this.verifier.verify(this.scopedContentConditionFalse, this.contentDto)).thenReturn(false);
	}

	@Test
	public void testThatLoadContentWorksForAllConditionsTrue() throws Exception {
		//given
		when(this.scopeDaoService.findByNameAndRequestContextPath(any(String.class), any(String.class))).thenReturn(this.scope);
		when(this.scopedContentDaoService.findByScope(any(Scope.class))).thenReturn(this.contentsReturnedByService);
		when(this.scopedContent.getConditions()).thenReturn(this.trueConditionsOfContent);
		when(this.scopedContent.getScopedContentItems()).thenReturn(this.contentItems);
		when(this.scopedContentItem.getConditions()).thenReturn(this.trueConditionsOfContent);

		//when
		this.toTest.loadContent(this.contentDto);

		//then
		verify(this.scopedContentParser).parse(this.contentItems);
	}

	@Test
	public void testThatLoadContentReturnsEmptyContentForAllContentConditionsTrueAndItemConditionsFalse() throws Exception {
		//given
		when(this.scopeDaoService.findByNameAndRequestContextPath(any(String.class), any(String.class))).thenReturn(this.scope);
		when(this.scopedContentDaoService.findByScope(any(Scope.class))).thenReturn(this.contentsReturnedByService);
		when(this.scopedContent.getConditions()).thenReturn(this.trueConditionsOfContent);
		when(this.scopedContent.getScopedContentItems()).thenReturn(this.contentItems);
		when(this.scopedContentItem.getConditions()).thenReturn(this.falseConditionsOfContent);

		//when
		this.toTest.loadContent(this.contentDto);

		//then
		verify(this.scopedContentParser).parse(argThat(new EmptyListMatcher<ScopedContentItem>()));
	}

	@Test
	public void testThatLoadContentReturnsContentForAllContentWithoutAnyConditions() throws Exception {
		//given
		when(this.scopeDaoService.findByNameAndRequestContextPath(any(String.class), any(String.class))).thenReturn(this.scope);
		when(this.scopedContentDaoService.findByScope(any(Scope.class))).thenReturn(this.contentsReturnedByService);
		when(this.scopedContent.getConditions()).thenReturn(new ArrayList<ScopedContentCondition>());

		//when
		this.toTest.loadContent(this.contentDto);

		//then
		verify(this.scopedContentParser).parse(argThat(new EmptyListMatcher<ScopedContentItem>()));
	}

	@Test
	public void testThatLoadContentReturnsEmptyContentForAllContentConditionsTrueAndNoItems() throws Exception {
		//given
		when(this.scopeDaoService.findByNameAndRequestContextPath(any(String.class), any(String.class))).thenReturn(this.scope);
		when(this.scopedContentDaoService.findByScope(any(Scope.class))).thenReturn(this.contentsReturnedByService);
		when(this.scopedContent.getConditions()).thenReturn(this.trueConditionsOfContent);
		when(this.scopedContent.getScopedContentItems()).thenReturn(new ArrayList<ScopedContentItem>());

		//when
		this.toTest.loadContent(this.contentDto);

		//then
		verify(this.scopedContentParser).parse(argThat(new EmptyListMatcher<ScopedContentItem>()));
	}

	@Test(expected = NoContentFoundException.class)
	public void testThatLoadContentThrowsNoContentFoundExceptionWhenNoContentConditionAreTrue() throws Exception {
		//given
		when(this.scopeDaoService.findByNameAndRequestContextPath(any(String.class), any(String.class))).thenReturn(this.scope);
		when(this.scopedContentDaoService.findByScope(any(Scope.class))).thenReturn(this.contentsReturnedByService);
		when(this.scopedContent.getConditions()).thenReturn(this.falseConditionsOfContent);

		//when
		this.toTest.loadContent(this.contentDto);
	}

	@Test(expected = NoScopeFoundException.class)
	public void testThatLoadContentThrowsNoScopeFoundExceptionWhenNoScopeIsFound() throws Exception {
		//given
		when(this.scopeDaoService.findByNameAndRequestContextPath(any(String.class), any(String.class))).thenReturn(null);

		//when
		this.toTest.loadContent(this.contentDto);
	}

	@SuppressWarnings("serial")
	@Test(expected = TooMuchContentFoundException.class)
	public void testThatLoadContentThrowsTooMuchContentFoundExceptionWhenContentConditionAreTrueForMoreThanOneContent() throws Exception {
		//given
		when(this.scopeDaoService.findByNameAndRequestContextPath(any(String.class), any(String.class))).thenReturn(this.scope);
		when(this.scopedContentDaoService.findByScope(any(Scope.class))).thenReturn(new ArrayList<ScopedContent>(){{
			this.add(ContentServiceTest.this.scopedContent);
			this.add(ContentServiceTest.this.scopedContent);
		}});
		when(this.scopedContent.getConditions()).thenReturn(this.trueConditionsOfContent);
		when(this.scopedContent.getScopedContentItems()).thenReturn(this.contentItems);
		when(this.scopedContentItem.getConditions()).thenReturn(this.trueConditionsOfContent);

		//when
		this.toTest.loadContent(this.contentDto);
	}
}
