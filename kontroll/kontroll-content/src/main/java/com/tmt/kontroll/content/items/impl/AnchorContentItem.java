package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.content.items.ContentItem;
import com.tmt.kontroll.content.persistence.selections.HtmlTag;

@Content(	tagClass = HtmlTag.class, tagValue = "Anchor")
public class AnchorContentItem extends ContentItem<HtmlTag> {

	public static final HtmlTag tag = HtmlTag.Anchor;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}
