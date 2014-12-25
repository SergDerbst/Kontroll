package com.tmt.kontroll.tools.content.ui.elements.editor.main.components;

import com.tmt.kontroll.tools.content.ui.elements.editor.ContentEditor;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * The actual container showing the current content to be edited by the {@link ContentEditor}.
 * It's part of the scrollable {@link CurrentContentPanel}.
 *
 * @author SergDerbst
 *
 */
@PageConfig(contexts = {@PageContext(scope = "page.contentEditor.form.main.currentContentPanel.currentContentContainer")})
public class CurrentContentContainer extends PageSegment {

}
