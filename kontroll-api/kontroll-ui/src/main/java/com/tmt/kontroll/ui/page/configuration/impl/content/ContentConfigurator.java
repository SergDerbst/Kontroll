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

/**
 * <p>
 * Configures {@link PageSegment}s annotated with {@link Content}.
 * </p>
 * <p>
 * The page segment will have a {@link ContentEditor} appended as first child. Furthermore, the configurator
 * checks, if a {@link Scope}, {@link ScopedContent} and a list of {@link ScopedContentItem}s already exist
 * in the data base. If not, these entities are initially created. The initial content item will be created
 * either according to the config given in the {@link Content} annotation or as a simple {@link ContentType#Text}
 * item with the segment's full scope name as value.
 * </p>
 *
 * @author SergDerbst
 *
 */
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
		final PageSegment contentEditor = this.makeContentEditor(segment);
		segment.getChildren().put(new PageSegmentOrdinalKey(ContentEditorOrdinal, contentEditor.getDomId()), contentEditor);
		for (final PageContext context : segment.getClass().getAnnotation(PageConfig.class).contexts()) {
			this.createScopeWithInitialContent(context.scope(), context.pattern(), segment.getClass().getAnnotation(Content.class));
		}
	}

	private PageSegment makeContentEditor(final PageSegment segment) {
		final ContentEditor editor = new ContentEditor();
		editor.setParentScope(segment.getDomId());
		editor.setScope("contentEditor");
		editor.getGeneralEvents().put(EventType.Click, this.configureEvent(segment));
		this.createScopeWithInitialContent(editor.getDomId(), "/", editor.getClass().getAnnotation(Content.class));
		return editor;
	}

	private PageEvent configureEvent(final PageSegment segment) {
		final PageEvent event = new PageEvent(EventType.Click, "toggleVisibility");
		event.getArguments().put("targetScope", "page.contentEditForm");
		event.getArguments().put("editScope", segment.getDomId());
		return event;
	}

	private void createScopeWithInitialContent(final String scopeName, final String pattern, final Content content) {
		Scope scope = this.scopeDaoService.findByNameAndRequestContextPath(scopeName, pattern);
		if (scope == null) {
			scope = new Scope();
			scope.setName(scopeName);
			scope.setRequestContextPath(pattern);
			this.scopeDaoService.create(scope);
			this.createInitialContent(scope, scopeName, content);
		}
	}

	private void createInitialContent(final Scope scope, final String scopeName, final Content content) {
		final ScopedContent scopedContent = new ScopedContent();
		final ArrayList<ScopedContent> contentList = new ArrayList<ScopedContent>();
		contentList.add(scopedContent);
		scopedContent.setScope(scope);
		scopedContent.setName(scopeName);
		scopedContent.setScopedContentItems(this.createInitialScopedContentItemList(contentList, scopeName, content));
		this.scopedContentDaoService.create(scopedContent);
	}

	private List<ScopedContentItem> createInitialScopedContentItemList(final List<ScopedContent> contents, final String scopeName, final Content content) {
		final ScopedContentItem item = new ScopedContentItem();
		final ArrayList<ScopedContentItem> itemList = new ArrayList<>();
		itemList.add(item);
		final String contentValue = content.content().isEmpty() ? scopeName : content.content();
		item.setContent(contentValue);
		item.setType(content.type());
		item.setScopedContents(contents);
		this.scopedContentDItemDaoService.create(item);
		return itemList;
	}
}
