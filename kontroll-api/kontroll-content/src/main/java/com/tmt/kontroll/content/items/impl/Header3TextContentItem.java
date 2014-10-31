package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.items.TextContentItem;

@Content(	tagClass = HtmlTag.class,
							tagValue = "Header3")
public class Header3TextContentItem extends TextContentItem {

	public static final HtmlTag tag = HtmlTag.Header3;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}
