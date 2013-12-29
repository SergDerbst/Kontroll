package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.content.items.TextContentItem;
import com.tmt.kontroll.content.persistence.selections.HtmlTag;

@Content(	tagClass = HtmlTag.class,
							tagValue = "Header6")
public class Header6TextContentItem extends TextContentItem {

	public static final HtmlTag tag = HtmlTag.Header6;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}
