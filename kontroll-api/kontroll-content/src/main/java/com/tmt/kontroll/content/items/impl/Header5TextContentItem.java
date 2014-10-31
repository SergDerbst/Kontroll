package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.items.TextContentItem;

@Content(	tagClass = HtmlTag.class,
							tagValue = "Header5")
public class Header5TextContentItem extends TextContentItem {

	public static final HtmlTag tag = HtmlTag.Header5;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}
