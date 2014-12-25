package com.tmt.kontroll.tools.content.request.handlers;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.services.ScopeDaoService;
import com.tmt.kontroll.context.annotations.RequestData;
import com.tmt.kontroll.context.annotations.RequestHandler;
import com.tmt.kontroll.context.request.handling.RequestHandling;
import com.tmt.kontroll.context.request.handling.RequestHandlingParam;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;
import com.tmt.kontroll.tools.content.data.ContentEditorSaveItemDto;
import com.tmt.kontroll.ui.page.segments.PageSegmentHolder;

@RequestHandler(patterns = {"/content/editor/item/save"}, handling = RequestHandling.Always, postHandling = {ContentEditorDataLoadingHandler.class})
@RequestData(ContentEditorSaveItemDto.class)
public class ContentEditorSaveItemHandler implements RequestHandlingService {

	@Autowired
	ScopeDaoService		scopeDaoService;

	@Autowired
	PageSegmentHolder	segmentHolder;

	@Override
	public void handle(final RequestHandlingParam param) {
		final ContentEditorSaveItemDto requestData = (ContentEditorSaveItemDto) param.getPayload().getRequestData();
		for (final Scope scope : this.scopeDaoService.findAllByName(requestData.getEditScope())) {
			scope.getScopedContents();
		}
		param.getDataResponse().put("segment", this.segmentHolder.fetchMatchingPageSegment(requestData.getEditScope(), requestData.getCurrentPath()));
	}
}
