package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.content.items.ContentItem;
import com.tmt.kontroll.content.persistence.selections.HtmlTag;

@Content(	tagClass = HtmlTag.class,
					tagValue = "Video")
public class VideoContentItem extends ContentItem<HtmlTag> {

	public static final HtmlTag tag = HtmlTag.Video;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}