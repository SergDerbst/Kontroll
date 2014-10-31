package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.items.ContentItem;

@Content(	tagClass = HtmlTag.class, tagValue = "Anchor")
public class AnchorContentItem extends ContentItem<HtmlTag> {

	public static final HtmlTag tag = HtmlTag.Anchor;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}
