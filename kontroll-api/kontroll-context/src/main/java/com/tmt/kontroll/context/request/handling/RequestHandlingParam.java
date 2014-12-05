package com.tmt.kontroll.context.request.handling;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tmt.kontroll.context.request.data.RequestContextDto;
import com.tmt.kontroll.context.request.data.RequestParamDto;
import com.tmt.kontroll.context.request.data.json.RequestDto;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;
import com.tmt.kontroll.context.session.SessionContext;

/**
 * A simple dto containing all the necessary data for any {@link RequestHandlingService} to handle
 * the request properly.
 *
 * @author SergDerbst
 *
 */
public class RequestHandlingParam {

	private RequestParamDto			requestParamDto;
	private RequestContextDto		requestContextDto;
	private SessionContext			sessionContext;
	private HttpServletRequest	request;
	private HttpSession					session;
	private String							requestPath;
	private Set<String>					dtoPaths;
	private RequestDto					payload;

	public RequestContextDto getRequestContextDto() {
		return this.requestContextDto;
	}

	public void setRequestContextDto(final RequestContextDto requestContextDto) {
		this.requestContextDto = requestContextDto;
	}

	public SessionContext getSessionContext() {
		return this.sessionContext;
	}

	public void setSessionContext(final SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

	public HttpSession getSession() {
		return this.session;
	}

	public void setSession(final HttpSession httpSession) {
		this.session = httpSession;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public void setRequest(final HttpServletRequest request) {
		this.request = request;
	}

	public String getRequestPath() {
		return this.requestPath;
	}

	public void setRequestPath(final String requestPath) {
		this.requestPath = requestPath;
	}

	public Set<String> getDtoPaths() {
		return this.dtoPaths;
	}

	public void setDtoPaths(final Set<String> dtoPaths) {
		this.dtoPaths = dtoPaths;
	}

	public RequestParamDto getRequestParamDto() {
		return this.requestParamDto;
	}

	public void setRequestParamDto(final RequestParamDto requestParamDto) {
		this.requestParamDto = requestParamDto;
	}

	public RequestDto getPayload() {
		return this.payload;
	}

	public void setPayload(final RequestDto payload) {
		this.payload = payload;
	}
}
