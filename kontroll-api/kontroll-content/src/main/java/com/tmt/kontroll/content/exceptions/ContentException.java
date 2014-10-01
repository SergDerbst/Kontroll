package com.tmt.kontroll.content.exceptions;

import org.apache.commons.lang3.exception.ContextedException;

public abstract class ContentException extends ContextedException {

	private static final long serialVersionUID = 153424600469013955L;

	public ContentException() {
		super();
	}

	public ContentException(final Throwable e) {
		super(e);
	}

	public ContentException(final String message,
	                        final String scopeName,
	                        final String requestPath) {
		super(message);
		super.addContextValue("scope name", scopeName);
		super.addContextValue("request path", requestPath);
	}
}
