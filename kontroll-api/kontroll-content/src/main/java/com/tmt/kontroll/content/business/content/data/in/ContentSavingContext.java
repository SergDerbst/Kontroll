package com.tmt.kontroll.content.business.content.data.in;

import java.util.List;

import com.tmt.kontroll.content.ContentItem;

public class ContentSavingContext {

	private final List<ContentItem>	content;
	private final String						scopeName;
	private final String						requestPattern;

	public ContentSavingContext(final List<ContentItem> content,
	                            final String scopeName,
	                            final String requestPattern) {
		this.content = content;
		this.scopeName = scopeName;
		this.requestPattern = requestPattern;
	}

	public List<ContentItem> getContent() {
		return this.content;
	}

	public String getScopeName() {
		return this.scopeName;
	}

	public String getRequestPattern() {
		return this.requestPattern;
	}
}
