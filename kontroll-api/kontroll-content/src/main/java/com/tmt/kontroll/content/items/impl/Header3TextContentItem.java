package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.content.items.TextContentItem;
import com.tmt.kontroll.content.persistence.selections.HtmlTag;

@Content(	tagClass = HtmlTag.class,
							tagValue = "Header3")
public class Header3TextContentItem extends TextContentItem {

	public static final HtmlTag tag = HtmlTag.Header3;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}
