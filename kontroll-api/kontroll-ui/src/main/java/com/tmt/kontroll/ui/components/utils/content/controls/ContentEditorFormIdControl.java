package com.tmt.kontroll.ui.components.utils.content.controls;

import com.tmt.kontroll.ui.components.utils.content.ContentEditorForm;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.HiddenControl;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;

/**
 * Hidden form element for the {@link ContentEditorForm} holding the id of the currently
 * edited content item.
 *
 * @author SergDerbst
 *
 */
@PageConfig(contexts = {@PageContext(scope = "page.contentEditorForm.id")})
@HiddenControl(name = "id")
public class ContentEditorFormIdControl extends PageSegment {

}
