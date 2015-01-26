package com.tmt.kontroll.tools.content.ui.elements.editor;

import com.tmt.kontroll.tools.content.data.ContentEditorDto;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.Form;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.Input;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label.Label;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.Select;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ItemsSource;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ValueSource;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.configuration.enums.components.ItemSourceType;
import com.tmt.kontroll.ui.page.configuration.enums.components.ValueSourceType;
import com.tmt.kontroll.ui.page.segments.PageSegment;

@PageConfig(contexts = {@PageContext(scope = "page.contentEditor.form")})
@Form(dtoClass = ContentEditorDto.class, name = "contentEditorForm", targetUrl = "/submit/contentEditor", submitHandlers = "saveContent")
@Input(ordinal = 0, position = ChildPosition.Top, name = "scope", readonly = true, valueSource = @ValueSource(type = ValueSourceType.Arg, source = "targetScope"))
@Select(ordinal = 1, position = ChildPosition.Top, name = "requestPattern", optionsSource = @ItemsSource(type = ItemSourceType.Custom))
@Input(ordinal = 2, position = ChildPosition.Bottom, name = "submit", type = "submit", label = @Label(@ValueSource(type = ValueSourceType.None)))
public class ContentEditorForm extends PageSegment {

}
