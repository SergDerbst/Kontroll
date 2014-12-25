package com.tmt.kontroll.tools.content.ui.elements.editor.main;

import com.tmt.kontroll.ui.page.configuration.annotations.components.containers.collections.List;
import com.tmt.kontroll.ui.page.configuration.annotations.components.devices.button.Button;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.configuration.annotations.event.Event;
import com.tmt.kontroll.ui.page.configuration.annotations.event.HandlerArgument;
import com.tmt.kontroll.ui.page.configuration.enums.components.ButtonType;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.segments.PageSegment;

@PageConfig(contexts = {@PageContext(scope = "page.contentEditor.form.main")})
@Button(ordinal = 2, position = ChildPosition.Bottom, name = "edit", events = {@Event(type = EventType.Click, handlers = {"prepareContentItemEdit", "toggleVisibility"})})
@Button(ordinal = 3, position = ChildPosition.Bottom, name = "add", type = ButtonType.Dropdown, events = {@Event(type = EventType.Click, handlers = {"dropdown"}, arguments = {@HandlerArgument(key = "dropdown", value = "adddropdown")})})
@List(ordinal = 4, position = ChildPosition.Bottom, name = "adddropdown", events = {@Event(type = EventType.Click, handlers = {"listItemClick"})})
@Button(ordinal = 5, position = ChildPosition.Bottom, name = "in", events = {@Event(type = EventType.Click, handlers = {"contentItemNav"})})
@Button(ordinal = 6, position = ChildPosition.Bottom, name = "out", events = {@Event(type = EventType.Click, handlers = {"contentItemNav"})})
@Button(ordinal = 7, position = ChildPosition.Bottom, name = "up", events = {@Event(type = EventType.Click, handlers = {"contentItemNav"})})
@Button(ordinal = 8, position = ChildPosition.Bottom, name = "down", events = {@Event(type = EventType.Click, handlers = {"contentItemNav"})})
public class ContentEditorMainPanel extends PageSegment {

}