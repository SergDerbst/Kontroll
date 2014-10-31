package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.items.ContentItem;

@Content(	tagClass = HtmlTag.class,
							tagValue = "Audio")
public class AudioContentItem extends ContentItem<HtmlTag> {

	public static final HtmlTag tag = HtmlTag.Audio;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}
