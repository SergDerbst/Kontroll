package com.tmt.kontroll.ui.page.configuration.impl.content;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.content.persistence.services.ScopeDaoService;
import com.tmt.kontroll.content.persistence.services.ScopedContentDaoService;
import com.tmt.kontroll.content.persistence.services.ScopedContentItemDaoService;
import com.tmt.kontroll.ui.components.utils.content.ContentEditor;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Content;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentOrdinalKey;

@Component
public class ContentConfigurator extends PageSegmentConfigurator {

	private static final int		ContentEditorOrdinal	= -1;

	@Autowired
	ScopeDaoService							scopeDaoService;

	@Autowired
	ScopedContentDaoService			scopedContentDaoService;

	@Autowired
	ScopedContentItemDaoService	scopedContentDItemDaoService;

	@Override
	protected boolean isResponsible(final PageSegment segment) {
		return segment.getClass().isAnnotationPresent(Content.class);
	}

	@Override
	protected void doConfiguration(final PageSegment segment) {
		segment.getChildren().put(new PageSegmentOrdinalKey(ContentEditorOrdinal, this.generateEditorScope(segment)), this.makeContentEditor(segment));
		for (final PageContext context : segment.getClass().getAnnotation(PageConfig.class).contexts()) {
			this.createScopeWithInitialContent(context);
		}
	}

	private PageSegment makeContentEditor(final PageSegment segment) {
		final ContentEditor editor = new ContentEditor();
		editor.setScope(this.generateEditorScope(segment));
		editor.getGeneralEvents().put(EventType.Click, this.configureEvent(segment));
		return editor;
	}

	private PageEvent configureEvent(final PageSegment segment) {
		final PageEvent event = new PageEvent(EventType.Click, "toggleVisibility");
		event.getArguments().put("targetScope", "page.contentEditForm");
		event.getArguments().put("editScope", segment.getDomId());
		return event;
	}

	private String generateEditorScope(final PageSegment segment) {
		return segment.getDomId() + "editor";
	}

	private void createScopeWithInitialContent(final PageContext context) {
		Scope scope = this.scopeDaoService.findByNameAndRequestContextPath(context.scope(), context.pattern());
		if (scope == null) {
			scope = new Scope();
			scope.setName(context.scope());
			scope.setRequestContextPath(context.pattern());
			this.scopeDaoService.create(scope);
			this.createInitialContent(scope, context);
		}
	}

	private void createInitialContent(final Scope scope, final PageContext context) {
		final ScopedContent content = new ScopedContent();
		final ArrayList<ScopedContent> contentList = new ArrayList<ScopedContent>();
		contentList.add(content);
		content.setScope(scope);
		content.setName(context.scope() + ".0");
		content.setScopedContentItems(this.createInitialScopedContentItemList(contentList, context));
		this.scopedContentDaoService.create(content);
	}

	private List<ScopedContentItem> createInitialScopedContentItemList(final List<ScopedContent> contents, final PageContext context) {
		final ScopedContentItem item = new ScopedContentItem();
		final ArrayList<ScopedContentItem> itemList = new ArrayList<>();
		itemList.add(item);
		item.setContent(context.scope());
		item.setType(ContentType.Text);
		item.setScopedContents(contents);
		this.scopedContentDItemDaoService.create(item);
		return itemList;
	}
}
