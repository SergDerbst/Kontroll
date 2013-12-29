package com.tmt.kontroll.content;

import java.util.Set;

import com.tmt.kontroll.context.global.GlobalContextDto;
import com.tmt.kontroll.context.request.RequestContextItem;

public class ContentDto {
	
	private final String scopeName;
	private final GlobalContextDto globalContextDto;
	private final Set<RequestContextItem> requestContext;
	
	public ContentDto(final Set<RequestContextItem> requestContext,
	                  final GlobalContextDto globalContextDto,
	                  final String scopeName) {
		this.requestContext = requestContext;
		this.globalContextDto = globalContextDto;
		this.scopeName = scopeName;
	}

	public String getScopeName() {
		return this.scopeName;
	}

	public Set<RequestContextItem> getRequestContext() {
		return this.requestContext;
	}

	public GlobalContextDto getGlobalContextDto() {
		return globalContextDto;
	}
}
