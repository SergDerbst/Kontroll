package com.tmt.kontroll.ui.page.layout.impl;

import java.util.List;

import com.tmt.kontroll.content.ContentService;
import com.tmt.kontroll.content.items.ContentItem;
import com.tmt.kontroll.ui.page.layout.PageSegment;

/**
 * A page layout container is a layout segment that contains nothing but
 * content loaded by the {@link ContentService}.
 * 
 * @author Sergio Weigel
 *
 */
public abstract class PageContent extends PageSegment {

	public List<ContentItem<? extends Enum<?>>> content;

	public List<ContentItem<? extends Enum<?>>> getContent() {
		return this.content;
	}

	public void setContent(final List<ContentItem<? extends Enum<?>>> content) {
		this.content = content;
	}
}
