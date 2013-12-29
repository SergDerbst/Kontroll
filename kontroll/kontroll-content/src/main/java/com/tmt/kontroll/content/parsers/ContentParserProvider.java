package com.tmt.kontroll.content.parsers;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.exceptions.NoContentParserFoundException;
import com.tmt.kontroll.content.items.ContentItem;
import com.tmt.kontroll.content.parsers.impl.AudioContentItemParser;
import com.tmt.kontroll.content.parsers.impl.ImageContentItemParser;
import com.tmt.kontroll.content.parsers.impl.TextContentItemParser;
import com.tmt.kontroll.content.parsers.impl.VideoContentItemParser;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.content.persistence.selections.ContentType;

@Component
public class ContentParserProvider {

	@Autowired
	AudioContentItemParser audioContentParser;

	@Autowired
	ImageContentItemParser imageContentParser;

	@Autowired
	TextContentItemParser textContentParser;

	@Autowired
	VideoContentItemParser videoContentParser;

	Map<ContentType, ContentItemParser<?>> contentParserMap = new EnumMap<ContentType, ContentItemParser<?>>(ContentType.class);

	@PostConstruct
	public void createContentParserMap() {
		this.contentParserMap.put(ContentType.Audio, this.audioContentParser);
		this.contentParserMap.put(ContentType.Image, this.imageContentParser);
		this.contentParserMap.put(ContentType.Text, this.textContentParser);
		this.contentParserMap.put(ContentType.Video, this.videoContentParser);
	}

	public ContentItem<?> provide(final ScopedContentItem scopedContentItem) throws NoContentParserFoundException {
		final ContentType type = scopedContentItem.getType();
		final ContentItemParser<?> contentParser = this.contentParserMap.get(type);
		if (contentParser != null) {
			return contentParser.parse(scopedContentItem);
		}
		throw NoContentParserFoundException.prepare(type);
	}
}
