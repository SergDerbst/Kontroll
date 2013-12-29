package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.content.items.TextContentItem;
import com.tmt.kontroll.content.persistence.selections.HtmlTag;

@Content(	tagClass = HtmlTag.class,
							tagValue = "Header2")
public class Header2TextContentItem extends TextContentItem {

	public static final HtmlTag tag = HtmlTag.Header2;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}
