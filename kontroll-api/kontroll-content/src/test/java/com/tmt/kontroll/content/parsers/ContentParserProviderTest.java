package com.tmt.kontroll.content.parsers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.content.exceptions.NoContentParserFoundException;
import com.tmt.kontroll.content.parsers.impl.AudioContentItemParser;
import com.tmt.kontroll.content.parsers.impl.ImageContentItemParser;
import com.tmt.kontroll.content.parsers.impl.TextContentItemParser;
import com.tmt.kontroll.content.parsers.impl.VideoContentItemParser;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.content.persistence.selections.HtmlTag;

public class ContentParserProviderTest {
	
	@Mock
	private AudioContentItemParser audioContentParser;
	@Mock
	private ImageContentItemParser imageContentParser;
	@Mock
	private TextContentItemParser textContentParser;
	@Mock
	private VideoContentItemParser videoContentParser;
	@Mock
	private Map<ContentType, ContentItemParser<?>> contentParserMap;
	@Mock
	private ScopedContentItem scopedContentItem;
	@Mock
	private ContentItemParser<HtmlTag> contentParser;
	
	private ContentParserProvider toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		when(this.scopedContentItem.getType()).thenReturn(ContentType.Audio);
		this.toTest = new ContentParserProvider();
		this.toTest.audioContentParser = this.audioContentParser;
		this.toTest.contentParserMap = this.contentParserMap;
		this.toTest.imageContentParser = this.imageContentParser;
		this.toTest.textContentParser = this.textContentParser;
		this.toTest.videoContentParser = this.videoContentParser;
	}

	@Test
	public void testCreateContentParserMap() throws Exception {
		//when
		this.toTest.createContentParserMap();
		
		//then
		verify(this.contentParserMap, times(4)).put(any(ContentType.class), any(ContentItemParser.class));
	}

	@Test
	@SuppressWarnings("serial")
	public void testThatProvisionWorksForExistingContentItemParser() throws Exception {
		//given
		this.toTest.contentParserMap = new HashMap<ContentType, ContentItemParser<?>>(){{
			put(ContentType.Audio, contentParser);
		}};
		
		//when
		this.toTest.provide(this.scopedContentItem);
		
		//then
		verify(this.contentParser).parse(this.scopedContentItem);
	}
	
	@Test(expected = NoContentParserFoundException.class)
	public void testThatNoContentParserFoundExceptionWillBeThrownForNonExistingContentItemParser() throws Exception {
		//given
		this.toTest.contentParserMap = new HashMap<ContentType, ContentItemParser<?>>();
		
		//when
		this.toTest.provide(this.scopedContentItem);
	}
}
