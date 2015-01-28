package com.tmt.kontroll.tools.content.ui.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.content.ScopeService;
import com.tmt.kontroll.content.business.content.ScopedContentItemService;
import com.tmt.kontroll.content.business.content.ScopedContentService;
import com.tmt.kontroll.tools.content.data.ContentEditorDataLoadingDto;
import com.tmt.kontroll.tools.content.ui.elements.ContentEditorToggle;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Content;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Creates the toggle for the content editor for {@link PageSegment}s with content.
 *
 * @author SergDerbst
 *
 */
@Component
public class ContentEditorToggleCreator {

	@Autowired
	ScopeService							scopeService;

	@Autowired
	ScopedContentService			scopedContentService;

	@Autowired
	ScopedContentItemService	scopedContentItemService;

	public PageSegment create(final PageSegment segment) {
		final ContentEditorToggle editor = new ContentEditorToggle();
		editor.setParentScope(segment.getDomId());
		editor.setScope("contentEditor");
		editor.getGeneralEvents().put(EventType.Click, this.configureEvent(segment));
		return editor;
	}

	private PageEvent configureEvent(final PageSegment segment) {
		final PageEvent event = new PageEvent(EventType.Click, new String[] {"prepareContentEditor", "toggleVisibility"});
		event.getArguments().put("targetScope", "page.contentEditor");
		event.getArguments().put("editScope", segment.getDomId());
		event.getArguments().put("dtoClass", ContentEditorDataLoadingDto.class.getName());
		return event;
	}

	private void createScopeWithInitialContent(final String scopeName, final String pattern, final Content content) {
		this.scopedContentItemService.init(this.scopedContentService.init(this.scopeService.init(scopeName, pattern)), content.content(), content.type());
	}
}
