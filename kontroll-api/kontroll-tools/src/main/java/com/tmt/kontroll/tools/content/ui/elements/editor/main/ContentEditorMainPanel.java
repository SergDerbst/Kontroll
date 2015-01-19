package com.tmt.kontroll.tools.content.ui.elements.editor.main;

import com.tmt.kontroll.tools.content.ui.elements.editor.main.components.ContentEditorAddDropdownItemProvider;
import com.tmt.kontroll.ui.page.configuration.annotations.components.containers.collections.Dropdown;
import com.tmt.kontroll.ui.page.configuration.annotations.components.devices.button.Button;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.configuration.annotations.event.Event;
import com.tmt.kontroll.ui.page.configuration.annotations.event.HandlerArgument;
import com.tmt.kontroll.ui.page.configuration.annotations.event.Trigger;
import com.tmt.kontroll.ui.page.configuration.enums.components.ButtonType;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.segments.PageSegment;

@PageConfig(contexts = {@PageContext(scope = "page.contentEditor.form.main")})
@Button(ordinal = 2, position = ChildPosition.Bottom, name = "edit", events = {@Event(type = EventType.Click, handlers = {"prepareContentEditor", "toggleVisibility"}, arguments = {@HandlerArgument(key = "purpose", value = "edit"), @HandlerArgument(key = "targetScope", value = "page.contentEditor.form.contentItemEditor")})})
@Button(ordinal = 3, position = ChildPosition.Bottom, name = "add", type = ButtonType.Dropdown, events = {@Event(type = EventType.Click, handlers = {"dropdown"}, targetScopes = {"adddropdown"})})
@Dropdown(ordinal = 4, position = ChildPosition.Bottom, name = "adddropdown", itemProvider = ContentEditorAddDropdownItemProvider.class, triggers = {@Trigger("page.contentEditor.form.main.add")})
@Button(ordinal = 5, position = ChildPosition.Bottom, name = "in", events = {@Event(type = EventType.Click, handlers = {"contentItemNav"})})
@Button(ordinal = 6, position = ChildPosition.Bottom, name = "out", events = {@Event(type = EventType.Click, handlers = {"contentItemNav"})})
@Button(ordinal = 7, position = ChildPosition.Bottom, name = "up", events = {@Event(type = EventType.Click, handlers = {"contentItemNav"})})
@Button(ordinal = 8, position = ChildPosition.Bottom, name = "down", events = {@Event(type = EventType.Click, handlers = {"contentItemNav"})})
public class ContentEditorMainPanel extends PageSegment {

}