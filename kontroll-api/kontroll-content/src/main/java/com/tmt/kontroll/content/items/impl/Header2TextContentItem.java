package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.items.TextContentItem;

@Content(	tagClass = HtmlTag.class,
							tagValue = "Header2")
public class Header2TextContentItem extends TextContentItem {

	public static final HtmlTag tag = HtmlTag.Header2;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}
