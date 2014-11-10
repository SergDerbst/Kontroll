package com.tmt.kontroll.ui.components.utils.content;

import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.ui.page.PageSegment;

/**
 * The content editor is a special {@link PageSegment} that triggers editing of page
 * segment's content by opening an edit form, when clicked.
 *
 * @author SergDerbst
 *
 */
public class ContentEditor extends PageSegment {

	public ContentEditor() {
		super();
		this.setEditImageContent();
	}

	private void setEditImageContent() {
		final ContentItem content = new ContentItem(HtmlTag.Image);
		content.getAttributes().put("src", "/images/general/content.editor.png");
	}
}
