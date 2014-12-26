package com.tmt.kontroll.tools.content.request.handlers;

import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmt.kontroll.content.business.content.ScopeService;
import com.tmt.kontroll.content.business.content.ScopedContentItemService;
import com.tmt.kontroll.content.business.content.ScopedContentService;
import com.tmt.kontroll.content.business.content.data.ContentOperatingContext;
import com.tmt.kontroll.content.business.content.exceptions.ContentUpdateFailedException;
import com.tmt.kontroll.content.parsers.ContentParser;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.context.annotations.RequestData;
import com.tmt.kontroll.context.annotations.RequestHandler;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.context.request.handling.RequestHandling;
import com.tmt.kontroll.context.request.handling.RequestHandlingParam;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;
import com.tmt.kontroll.tools.content.data.ContentEditorSaveItemDto;
import com.tmt.kontroll.ui.page.segments.PageSegment;
import com.tmt.kontroll.ui.page.segments.PageSegmentChildrenAndContentAccessor;
import com.tmt.kontroll.ui.page.segments.PageSegmentHolder;

@RequestHandler(patterns = {"/content/editor/item/save"}, handling = RequestHandling.Always, postHandling = {ContentEditorDataLoadingHandler.class})
@RequestData(ContentEditorSaveItemDto.class)
public class ContentEditorSaveItemHandler implements RequestHandlingService {

	@Autowired
	ScopeService													scopeService;
	@Autowired
	ScopedContentService									scopedContentService;
	@Autowired
	ScopedContentItemService							scopedContentItemService;
	@Autowired
	PageSegmentHolder											segmentHolder;
	@Autowired
	GlobalContext													globalContext;
	@Autowired
	PageSegmentChildrenAndContentAccessor	childrenAndContentHandler;
	@Autowired
	ContentParser													contentParser;

	@Override
	public void handle(final RequestHandlingParam param) {
		final ContentEditorSaveItemDto requestData = (ContentEditorSaveItemDto) param.getPayload().getRequestData();
		final PageSegment segment = this.segmentHolder.fetchMatchingPageSegment(requestData.getEditScope(), requestData.getEditScopePattern());
		final ContentOperatingContext context = this.createContentLoadingContext(requestData);
		this.contentParser.parse(this.handleContent(requestData, context), segment.getDomId());
		param.getDataResponse().put("segment", segment);
	}

	private Set<ScopedContentItem> handleContent(final ContentEditorSaveItemDto requestData, final ContentOperatingContext context) {
		final Scope scope = this.scopeService.load(requestData.getEditScope(), requestData.getEditScopePattern());
		boolean updated = false;
		final Set<ScopedContentItem> validItems = this.scopedContentItemService.fetchValidItems(this.scopedContentService.loadValidContent(scope, context), context);
		for (final ScopedContentItem item : validItems) {
			if (requestData.getItemNumber().equals(item.getItemNumber())) {
				final ScopedContentItem updatedItem = this.updateItem(item, requestData);
				validItems.remove(item);
				validItems.add(updatedItem);
				updated = true;
			}
		}
		if (!updated) {
			validItems.add(this.addItem(requestData));
		}
		return validItems;
	}

	private ScopedContentItem addItem(final ContentEditorSaveItemDto requestData) {
		try {
			return this.scopedContentItemService.add(this.setValuesOnItem(new ScopedContentItem(), requestData));
		} catch (final Exception e) {
			throw new ContentUpdateFailedException(e);
		}
	}

	private ScopedContentItem updateItem(final ScopedContentItem item, final ContentEditorSaveItemDto requestData) {
		try {
			return this.scopedContentItemService.write(this.setValuesOnItem(item, requestData));
		} catch (final Exception e) {
			throw new ContentUpdateFailedException(e);
		}
	}

	private ScopedContentItem setValuesOnItem(final ScopedContentItem item, final ContentEditorSaveItemDto requestData) {
		item.setContent(requestData.getContent());
		item.setTagName(requestData.getContentTag().getTagName());
		item.setType(requestData.getContentType());
		item.setCssClass(requestData.getCss());
		item.setItemNumber(requestData.getItemNumber());
		return item;
	}

	private ContentOperatingContext createContentLoadingContext(final ContentEditorSaveItemDto requestData) {
		final Pattern pattern = Pattern.compile(requestData.getEditScopePattern());
		return new ContentOperatingContext(this.globalContext.requestContextHolder().fetchRequestContext(pattern), this.globalContext.globalContext(), null, pattern, requestData.getEditScope());
	}
}
