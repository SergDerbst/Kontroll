package com.tmt.kontroll.web.request.handlers.forms.content;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.services.ScopeDaoService;
import com.tmt.kontroll.context.annotations.RequestData;
import com.tmt.kontroll.context.annotations.RequestHandler;
import com.tmt.kontroll.context.exceptions.RequestHandlingException;
import com.tmt.kontroll.context.request.handling.RequestHandling;
import com.tmt.kontroll.context.request.handling.RequestHandlingParam;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;
import com.tmt.kontroll.ui.components.utils.content.data.ContentEditorDto;

@RequestHandler(patterns = {"/submit/contentEditor"}, handling = RequestHandling.Always)
@RequestData(ContentEditorDto.class)
public class ContentEditorFormSubmitDtoHandler implements RequestHandlingService {

	@Autowired
	ScopeDaoService	scopeDaoService;

	@Override
	public void handle(final RequestHandlingParam param) {
		try {
			final ContentEditorDto requestData = (ContentEditorDto) param.getPayload().getRequestData();
			Scope scope = this.scopeDaoService.findByNameAndRequestContextPath(requestData.getTargetScope(), requestData.getRequestPatterns());
			if (scope == null) {
				scope = new Scope();
			}
		} catch (final Exception e) {
			throw new RequestHandlingException(e);
		}
	}
}
