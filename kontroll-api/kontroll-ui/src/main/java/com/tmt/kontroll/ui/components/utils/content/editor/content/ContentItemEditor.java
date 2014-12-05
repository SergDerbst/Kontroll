package com.tmt.kontroll.ui.components.utils.content.editor.content;

import com.tmt.kontroll.ui.page.configuration.annotations.components.devices.button.Button;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.Input;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label.Label;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.Select;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ItemsSource;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ValueSource;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.configuration.annotations.event.Event;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.configuration.enums.components.ItemSourceType;
import com.tmt.kontroll.ui.page.configuration.enums.components.ValueSourceType;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.segments.PageSegment;

@PageConfig(contexts = {@PageContext(scope = "page.contentEditor.form.contentItemEditor")})
@Select(ordinal = 0, position = ChildPosition.Top, name = "type", optionsSource = @ItemsSource(type = ItemSourceType.Property, source = "optionsMap"))
@Input(ordinal = 1, position = ChildPosition.Bottom, name = "cssClass", type = "text", label = @Label(valueSource = @ValueSource(type = ValueSourceType.Caption)))
@Button(ordinal = 2, position = ChildPosition.Bottom, name = "manageConditions", events = {@Event(type = EventType.Click, handlers = {"toggleVisibility"}, targetScopes = {"page.contentEditor.form.contentItemEditor"})})
public class ContentItemEditor extends PageSegment {

}