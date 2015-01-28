package com.tmt.kontroll.tools.content.ui.elements.editor.main.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.caption.CaptionService;
import com.tmt.kontroll.ui.page.configuration.helpers.providers.ItemProvider;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.segments.PageSegment;

@Component
public class ContentEditorAddDropdownItemProvider implements ItemProvider {

	@Autowired
	CaptionService	captionService;

	@Override
	public List<PageSegment> provide(final PageSegment parentSegment) {
		final List<PageSegment> items = new ArrayList<>();
		items.add(this.makeItem(parentSegment, "after"));
		items.add(this.makeItem(parentSegment, "before"));
		items.add(this.makeItem(parentSegment, "child"));
		items.add(this.makeItem(parentSegment, "same"));
		return items;
	}

	private PageSegment makeItem(final PageSegment parentSegment, final String scopeName) {
		final PageSegment item = new PageSegment() {};
		item.setParentScope(parentSegment.getDomId());
		item.setScope(scopeName);
		item.setCaptionIdentifier(item.getDomId());
		this.captionService.init(item.getCaptionIdentifier(), StringUtils.capitalize(scopeName));
		this.makeEvent(scopeName, item);
		return item;
	}

	private void makeEvent(final String scopeName, final PageSegment item) {
		final PageEvent event = new PageEvent(EventType.Click, new String[] {"prepareContentEditor", "toggleVisibility"});
		event.getArguments().put("targetScope", "page.contentEditor.form.contentItemEditor");
		event.getArguments().put("location", scopeName);
		event.getArguments().put("purpose", "add");
		event.getArguments().put("visible", "true");
		item.getGeneralEvents().put(event.getType(), event);
	}
}
