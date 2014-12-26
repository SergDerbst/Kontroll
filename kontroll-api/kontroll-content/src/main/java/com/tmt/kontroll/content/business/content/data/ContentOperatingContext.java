package com.tmt.kontroll.content.business.content.data;

import java.util.Set;
import java.util.regex.Pattern;

import com.tmt.kontroll.context.global.GlobalContextDto;
import com.tmt.kontroll.context.request.RequestContextItem;

public class ContentOperatingContext {

	private final String									scopeName;
	private final String									requestPath;
	private final Pattern									requestPattern;
	private final GlobalContextDto				globalContextDto;
	private final Set<RequestContextItem>	requestContext;

	public ContentOperatingContext(	final Set<RequestContextItem> requestContext,
	                             	final GlobalContextDto globalContextDto,
	                             	final String requestPath,
	                             	final Pattern requestPattern,
	                             	final String scopeName) {
		this.requestContext = requestContext;
		this.globalContextDto = globalContextDto;
		this.requestPath = requestPath;
		this.requestPattern = requestPattern;
		this.scopeName = scopeName;
	}

	public String getScopeName() {
		return this.scopeName;
	}

	public Set<RequestContextItem> getRequestContext() {
		return this.requestContext;
	}

	public GlobalContextDto getGlobalContextDto() {
		return this.globalContextDto;
	}

	public String getRequestPath() {
		return this.requestPath;
	}

	public Pattern getRequestPattern() {
		return this.requestPattern;
	}
}
