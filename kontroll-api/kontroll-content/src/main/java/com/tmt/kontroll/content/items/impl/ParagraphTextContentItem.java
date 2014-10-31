package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.items.TextContentItem;

@Content(	tagClass = HtmlTag.class,
							tagValue = "Paragraph")
public class ParagraphTextContentItem extends TextContentItem {

	public static final HtmlTag tag = HtmlTag.Paragraph;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}
