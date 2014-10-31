package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.items.ContentItem;

@Content(	tagClass = HtmlTag.class,
							tagValue = "Image")
public class ImageContentItem extends ContentItem<HtmlTag> {

	public static final HtmlTag tag = HtmlTag.Image;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}
