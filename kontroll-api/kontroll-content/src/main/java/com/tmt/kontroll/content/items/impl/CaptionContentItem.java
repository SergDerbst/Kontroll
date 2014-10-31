package com.tmt.kontroll.content.items.impl;

import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.items.TextContentItem;

public class CaptionContentItem extends TextContentItem {

	@Override
	public HtmlTag getTag() {
		return HtmlTag.Div;
	}
}
