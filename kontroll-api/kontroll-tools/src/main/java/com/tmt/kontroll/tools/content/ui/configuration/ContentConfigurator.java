package com.tmt.kontroll.tools.content.ui.configuration;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.content.ScopeService;
import com.tmt.kontroll.content.business.content.ScopedContentItemService;
import com.tmt.kontroll.content.business.content.ScopedContentService;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.tools.content.data.ContentEditorDataLoadingDto;
import com.tmt.kontroll.tools.content.ui.elements.ContentEditorToggle;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Content;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.segments.PageSegment;
import com.tmt.kontroll.ui.page.segments.PageSegmentChildrenAndContentAccessor;

/**
 * <p>
 * Configures {@link PageSegment}s annotated with {@link Content}.
 * </p>
 * <p>
 * The page segment will have a {@link ContentEditorToggle} appended as first child. Furthermore, the configurator
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

	@Autowired
	ScopeService													scopeService;

	@Autowired
	ScopedContentService									scopedContentService;

	@Autowired
	ScopedContentItemService							scopedContentItemService;

	@Autowired
	PageSegmentChildrenAndContentAccessor	childrenAndContentAccessor;

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return Content.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		this.childrenAndContentAccessor.fetchBottomChildren(segment).add(this.makeContentEditor(segment));
		for (final PageContext context : segment.getClass().getAnnotation(PageConfig.class).contexts()) {
			this.createScopeWithInitialContent(context.scope(), context.pattern(), segment.getClass().getAnnotation(Content.class));
		}
	}

	private PageSegment makeContentEditor(final PageSegment segment) {
		final ContentEditorToggle editor = new ContentEditorToggle();
		editor.setParentScope(segment.getDomId());
		editor.setScope("contentEditor");
		editor.getGeneralEvents().put(EventType.Click, this.configureEvent(segment));
		this.createScopeWithInitialContent(editor.getDomId(), "/", editor.getClass().getAnnotation(Content.class));
		return editor;
	}

	private void createScopeWithInitialContent(final String scopeName, final String pattern, final Content content) {
		this.scopedContentItemService.init(this.scopedContentService.init(this.scopeService.init(scopeName, pattern)), content.content(), content.type());
	}

	private PageEvent configureEvent(final PageSegment segment) {
		final PageEvent event = new PageEvent(EventType.Click, new String[] {"prepareContentEditor", "toggleVisibility"});
		event.getArguments().put("targetScope", "page.contentEditor");
		event.getArguments().put("editScope", segment.getDomId());
		event.getArguments().put("dtoClass", ContentEditorDataLoadingDto.class.getName());
		return event;
	}
}
