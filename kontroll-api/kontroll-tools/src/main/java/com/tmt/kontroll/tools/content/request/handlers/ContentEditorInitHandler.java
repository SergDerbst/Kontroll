package com.tmt.kontroll.tools.content.request.handlers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmt.kontroll.content.business.content.ContentService;
import com.tmt.kontroll.content.business.content.data.out.EditedScope;
import com.tmt.kontroll.context.annotations.RequestData;
import com.tmt.kontroll.context.annotations.RequestHandler;
import com.tmt.kontroll.context.request.handling.RequestHandling;
import com.tmt.kontroll.context.request.handling.RequestHandlingParam;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;
import com.tmt.kontroll.tools.content.data.ContentEditorInitDto;
import com.tmt.kontroll.ui.page.segments.PageSegmentChildrenAndContentAccessor;

@RequestHandler(patterns = {"/content/editor/init"}, handling = RequestHandling.Always)
@RequestData(ContentEditorInitDto.class)
public class ContentEditorInitHandler implements RequestHandlingService {

	@Autowired
	ContentService												contentService;

	@Autowired
	PageSegmentChildrenAndContentAccessor	childrenAndContentAccessor;

	@Override
	public void handle(final RequestHandlingParam param) {
		final Set<EditedScope> scopes = this.contentService.loadContentForEditing(((ContentEditorInitDto) param.getPayload().getRequestData()).getScope());
		param.getDataResponse().put("scopes", scopes);
	}
}