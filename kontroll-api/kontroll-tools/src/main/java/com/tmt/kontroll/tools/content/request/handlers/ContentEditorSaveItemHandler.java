package com.tmt.kontroll.tools.content.request.handlers;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmt.kontroll.content.business.content.ContentService;
import com.tmt.kontroll.content.business.content.data.in.ContentSavingContext;
import com.tmt.kontroll.context.annotations.RequestData;
import com.tmt.kontroll.context.annotations.RequestHandler;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.context.request.handling.RequestHandling;
import com.tmt.kontroll.context.request.handling.RequestHandlingParam;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;
import com.tmt.kontroll.tools.content.data.ContentEditorDto;
import com.tmt.kontroll.ui.page.segments.PageSegmentHolder;

@RequestHandler(patterns = {"/content/editor/save"}, handling = RequestHandling.Always, postHandling = {ContentEditorInitHandler.class})
@RequestData(ContentEditorDto.class)
public class ContentEditorSaveItemHandler implements RequestHandlingService {

	@Autowired
	PageSegmentHolder	segmentHolder;
	@Autowired
	GlobalContext			globalContext;
	@Autowired
	ContentService		contentService;

	@Override
	public void handle(final RequestHandlingParam param) {
		final ContentEditorDto requestData = (ContentEditorDto) param.getPayload().getRequestData();
		param.getDataResponse().put("scope", this.contentService.saveContent(this.createContentSavingContext(requestData)));
	}

	private ContentSavingContext createContentSavingContext(final ContentEditorDto requestData) {
		return new ContentSavingContext(requestData.getContent(), requestData.getScope(), requestData.getRequestPattern(), requestData.getCurrent());
	}
}
