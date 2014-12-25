package com.tmt.kontroll.web.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.context.global.GlobalContextDto;
import com.tmt.kontroll.context.request.handling.RequestHandlingParam;
import com.tmt.kontroll.context.request.handling.coordination.RequestHandlingCoordinator;
import com.tmt.kontroll.context.session.SessionContext;
import com.tmt.kontroll.ui.page.management.PageManager;
import com.tmt.kontroll.ui.page.segments.PageSegment;

@Component
public class ResponseBuilder {

	@Autowired
	GlobalContext								globalContext;

	@Autowired
	PageManager									pageManager;

	@Autowired
	RequestHandlingCoordinator	requestHandlingCoordinator;

	public ResponseDto build(final String requestPath, final HttpSession session, final HttpServletRequest request) throws Exception {
		if (!requestPath.contains("favicon")) {
			final RequestHandlingParam param = this.constructParameterObject(requestPath, session, request);
			this.requestHandlingCoordinator.coordinate(param);
			return this.constructResponseObject(param);
		}
		return null;
	}

	private ResponseDto constructResponseObject(final RequestHandlingParam param) throws Exception {
		final GlobalContextDto global = this.globalContext.globalContext();
		final SessionContext session = this.globalContext.sessionContextHolder().sessionContext(param.getSession().getId());
		if (param.getDataResponse().isEmpty()) {
			final PageSegment segment = this.pageManager.manage(param.getRequestPath(), param.getPayload().getTargetScope(), param.getSession().getId());
			return new ResponseDto(global, session, segment);
		} else {
			return new ResponseDto(global, session, param.getDataResponse());
		}
	}

	private RequestHandlingParam constructParameterObject(final String requestPath, final HttpSession session, final HttpServletRequest request) {
		final RequestHandlingParam param = new RequestHandlingParam();
		param.setRequest(request);
		param.setSession(session);
		param.setRequestPath(requestPath);
		return param;
	}
}
