package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.content.items.TextContentItem;
import com.tmt.kontroll.content.persistence.selections.HtmlTag;

@Content(	tagClass = HtmlTag.class,
							tagValue = "Paragraph")
public class ParagraphTextContentItem extends TextContentItem {

	public static final HtmlTag tag = HtmlTag.Paragraph;

	@Override
	public HtmlTag getTag() {
		return tag;
	}
}
