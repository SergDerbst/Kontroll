package com.tmt.kontroll.ui.components.utils.content.controls;

import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.TextControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label.Label;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;

@PageConfig(contexts = {@PageContext(scope = "page.contentEditorForm.cssClass")})
@TextControl(name = "cssClass")
@Label("page.contentEditorForm.content.cssClass.caption")
public class ContentEditorFormCssClassControl extends PageSegment {

}
