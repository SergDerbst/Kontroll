package com.tmt.kontroll.ui.components.utils.content;

import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Content;

/**
 * The content editor is a special {@link PageSegment} that triggers editing of page
 * segment's content by opening the {@link ContentEditorForm}, when clicked.
 *
 * @author SergDerbst
 *
 */
@Content(type = ContentType.Image, content = "/images/general/content/editor.png")
public class ContentEditor extends PageSegment {

	public ContentEditor() {
		super();
	}
}
