package com.tmt.kontroll.ui.components.utils.content.controls;

import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label.Label;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.SelectControl;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;

@PageConfig(contexts = {@PageContext(scope = "page.contentEditorForm.htmlTag")})
@SelectControl(name = "htmlTag")
@Label("page.contentEditorForm.htmlTag.label.caption")
public class ContentEditorFormTagControl extends PageSegment {

}