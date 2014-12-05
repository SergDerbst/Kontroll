package com.tmt.kontroll.context.request.data.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmt.kontroll.context.session.SessionContext;

public class RequestDto {

	@JsonProperty("session")
	private SessionContext			sessionContext;
	@JsonProperty("request")
	private DataTransferElement	requestData;
	private String							targetScope;

	public SessionContext getSessionContext() {
		return this.sessionContext;
	}

	public void setSessionContext(final SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

	public DataTransferElement getRequestData() {
		return this.requestData;
	}

	public void setRequestData(final DataTransferElement requestData) {
		this.requestData = requestData;
	}

	public String getTargetScope() {
		return this.targetScope;
	}

	public void setTargetScope(final String targetScope) {
		this.targetScope = targetScope;
	}
}
