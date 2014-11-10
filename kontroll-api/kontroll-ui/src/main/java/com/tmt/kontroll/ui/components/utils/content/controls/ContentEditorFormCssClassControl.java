package com.tmt.kontroll.ui.components.utils.content.controls;

import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.TextControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label.Label;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;

@PageConfig(contexts = {@PageContext(scope = "page.contentEditorForm.cssClass", ordinal = 3)})
@TextControl(name = "cssClass")
@Label
public class ContentEditorFormCssClassControl extends PageSegment {

}
