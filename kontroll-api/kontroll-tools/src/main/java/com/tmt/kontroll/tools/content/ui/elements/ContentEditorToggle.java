package com.tmt.kontroll.tools.content.ui.elements;

import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.tools.content.ui.elements.editor.ContentEditorForm;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Content;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * The content editor is a special {@link PageSegment} that triggers editing of page
 * segment's content by opening the {@link ContentEditorForm}, when clicked.
 *
 * @author SergDerbst
 *
 */
@Content(type = ContentType.Image, content = "/images/general/content/editor.jpg")
public class ContentEditorToggle extends PageSegment {

}
