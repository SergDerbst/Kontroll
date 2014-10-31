package com.tmt.kontroll.content.items;


import org.junit.Before;

import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.persistence.selections.ContentChildrenOrder;
import com.tmt.kontroll.test.ObjectDataTest;

public class ContentItemTest extends ObjectDataTest<ContentItem<HtmlTag>>{

	public ContentItemTest() {
		super(new ContentItem<HtmlTag>() {
			@Override
			public HtmlTag getTag() {
				return HtmlTag.Anchor;
			}
		});
	}
	
	@Before
	public void setUp() throws Exception {
		this.getFieldByTypeReferenceMap().put(ContentChildrenOrder.class, ContentChildrenOrder.ContentBeforeChildren);
	}
}
