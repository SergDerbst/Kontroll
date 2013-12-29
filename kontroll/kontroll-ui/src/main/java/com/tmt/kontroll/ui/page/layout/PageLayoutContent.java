package com.tmt.kontroll.ui.page.layout;

import java.util.List;

import com.tmt.kontroll.content.items.ContentItem;

/**
 * A page layout container is a layout segment that contains nothing but 
 * content loaded by the {@link ScopedContentService}. 
 * 
 * @author Serg Derbst
 *
 */
public abstract class PageLayoutContent extends PageLayoutSegment {
	
	public List<ContentItem<? extends Enum<?>>> content;

	public List<ContentItem<? extends Enum<?>>> getContent() {
		return content;
	}

	public void setContent(List<ContentItem<? extends Enum<?>>> content) {
		this.content = content;
	}	
}
