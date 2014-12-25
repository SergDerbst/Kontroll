package com.tmt.kontroll.tools.content.ui.elements.editor.content;

import java.util.HashMap;
import java.util.Map;

import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.tools.content.data.ContentEditorSaveItemDto;
import com.tmt.kontroll.ui.page.configuration.annotations.components.devices.button.Button;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.Input;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label.Label;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.HierarchicalSelect;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.textarea.Textarea;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ValueSource;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.configuration.annotations.event.Event;
import com.tmt.kontroll.ui.page.configuration.annotations.general.Hidden;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.configuration.enums.components.ValueSourceType;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.segments.PageSegment;

@Hidden
@PageConfig(contexts = {@PageContext(scope = "page.contentEditor.form.contentItemEditor", ordinal = 1)})
@HierarchicalSelect(ordinal = 0, position = ChildPosition.Top, name = "type")
@Input(ordinal = 1, position = ChildPosition.Top, name = "cssClass", type = "text", label = @Label(value = @ValueSource(type = ValueSourceType.Caption)))
@Textarea(ordinal = 2, position = ChildPosition.Top, name = "content", valueSource = @ValueSource(type = ValueSourceType.Custom))
@Button(ordinal = 3, position = ChildPosition.Bottom, name = "manageConditions", events = {@Event(type = EventType.Click, handlers = {"toggleVisibility"}, targetScopes = {"page.contentEditor.form.contentItemEditor"})})
@Button(ordinal = 4, position = ChildPosition.Bottom, name = "saveContentItem", events = {@Event(type = EventType.Click, handlers = {"saveContentItem"}, dto = ContentEditorSaveItemDto.class)})
public class ContentItemEditor extends PageSegment {

	private final Map<String, String[]>	optionsMap	= new HashMap<>();

	public ContentItemEditor() {
		this.createOptionsMap();
	}

	public Map<String, String[]> getOptionsMap() {
		return this.optionsMap;
	}

	private void createOptionsMap() {
		this.optionsMap.put(ContentType.Audio.getName(), new String[] {HtmlTag.Audio.getTagName()});
		this.optionsMap.put(ContentType.Image.getName(), this.createImageTags());
		this.optionsMap.put(ContentType.Text.getName(), this.createTextTags());
		this.optionsMap.put(ContentType.Video.getName(), new String[] {HtmlTag.Video.getTagName()});
	}

	private String[] createImageTags() {
		return new String[] {HtmlTag.Anchor.getTagName(), HtmlTag.Image.getTagName()};
	}

	private String[] createTextTags() {
		return new String[] {HtmlTag.Anchor.getTagName(), HtmlTag.Bold.getTagName(), HtmlTag.Div.getTagName(), HtmlTag.Header1.getTagName(), HtmlTag.Header2.getTagName(), HtmlTag.Header3.getTagName(), HtmlTag.Header4.getTagName(), HtmlTag.Header5.getTagName(), HtmlTag.Header6.getTagName(), HtmlTag.Italic.getTagName(), HtmlTag.Paragraph.getTagName(), HtmlTag.Span.getTagName()};
	}
}