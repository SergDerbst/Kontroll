package com.tmt.kontroll.ui.page.configuration.impl.components.text;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.content.persistence.services.CaptionDaoService;
import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.page.configuration.annotations.components.text.Header;
import com.tmt.kontroll.ui.page.configuration.impl.components.layout.ChildElementConfigurator;
import com.tmt.kontroll.ui.page.segments.PageSegment;
import com.tmt.kontroll.ui.page.segments.PageSegmentHolder;

/**
 * Configurator for {@link PageSegment}s annotated with {@link Header}. It adds a header
 * child element to the page segment according to the given annotation values.
 *
 * @author SergDerbst
 *
 */
@Component
public class HeaderConfigurator extends ChildElementConfigurator {

	@Autowired
	CaptionDaoService										captionDaoService;

	@Autowired
	PageSegmentHolder										pageSegmentHolder;

	@SuppressWarnings("serial")
	private final Map<Integer, HtmlTag>	tagMap	= new HashMap<Integer, HtmlTag>() {
		{
			this.put(1, HtmlTag.Header1);
			this.put(2, HtmlTag.Header2);
			this.put(3, HtmlTag.Header3);
			this.put(4, HtmlTag.Header4);
			this.put(5, HtmlTag.Header5);
			this.put(6, HtmlTag.Header6);
		}
	};

	public HeaderConfigurator() {
		super(Header.class);
	}

	@Override
	public void configure(final PageSegment segment) {
		final Header config = segment.getClass().getAnnotation(Header.class);
		final PageSegment header = new PageSegment(this.tagMap.get(config.level())) {};
		header.setParentScope(segment.getDomId());
		header.setScope(config.name());
		this.handleCaption(config, header);
		super.addChild(config.position(), segment, header);
		this.pageSegmentHolder.addPageSegment(header.getDomId(), header);
	}

	private void handleCaption(final Header config, final PageSegment header) {
		String captionIdentifier = config.caption();
		if (captionIdentifier.isEmpty()) {
			captionIdentifier = header.getDomId();
		}
		header.setCaptionIdentifier(captionIdentifier);
		Caption caption = this.captionDaoService.findByIdentifierAndLocale(captionIdentifier, Locale.US);
		if (caption == null) {
			caption = new Caption();
			caption.setIdentifier(captionIdentifier);
			caption.setLocale(Locale.US);
			caption.setText(config.name());
			this.captionDaoService.create(caption);
		}
	}
}
