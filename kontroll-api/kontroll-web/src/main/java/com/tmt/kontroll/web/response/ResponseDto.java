package com.tmt.kontroll.web.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tmt.kontroll.context.global.GlobalContextDto;
import com.tmt.kontroll.context.session.SessionContext;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Response DTO for all responses to the client.
 *
 * @author SergDerbst
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class ResponseDto {

	private final GlobalContextDto		global;
	private final SessionContext			session;
	private final PageSegment					segment;
	private final Map<String, Object>	dataResponse;

	public ResponseDto(	final GlobalContextDto global,
											final SessionContext session,
											final Map<String, Object> dataResponse) {
		this.global = global;
		this.session = session;
		this.segment = null;
		this.dataResponse = dataResponse;
	}

	public ResponseDto(	final GlobalContextDto global,
											final SessionContext session,
											final PageSegment segment) {
		this.global = global;
		this.session = session;
		this.segment = segment;
		this.dataResponse = null;
	}

	public GlobalContextDto getGlobal() {
		return this.global;
	}

	public SessionContext getSession() {
		return this.session;
	}

	public PageSegment getSegment() {
		return this.segment;
	}

	public Map<String, Object> getDataResponse() {
		return this.dataResponse;
	}
}
