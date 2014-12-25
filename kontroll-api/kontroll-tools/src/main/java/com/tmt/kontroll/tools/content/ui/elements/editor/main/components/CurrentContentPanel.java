package com.tmt.kontroll.tools.content.ui.elements.editor.main.components;

import com.tmt.kontroll.tools.content.ui.elements.editor.ContentEditor;
import com.tmt.kontroll.ui.page.configuration.annotations.components.containers.Scrollable;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.Select;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ItemsSource;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.configuration.enums.components.ItemSourceType;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Part of the {@link ContentEditor}. The panel showing the current content of the
 * scope to be edited. It offers the possibility to browse throw the content according
 * to its hierarchical numeric bullets.
 *
 * @author SergDerbst
 *
 */
@PageConfig(contexts = {@PageContext(scope = "page.contentEditor.form.main.currentContentPanel")})
@Select(ordinal = 0, position = ChildPosition.Top, name = "contentItemNumber", optionsSource = @ItemsSource(type = ItemSourceType.Custom))
@Scrollable
public class CurrentContentPanel extends PageSegment {

}
