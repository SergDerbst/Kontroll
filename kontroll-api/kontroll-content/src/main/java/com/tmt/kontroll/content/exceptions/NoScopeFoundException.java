package com.tmt.kontroll.content.exceptions;

import java.util.regex.Pattern;

public class NoScopeFoundException extends ContentException {

	private static final long	serialVersionUID	= 3813948074183446602L;

	public static NoScopeFoundException prepare(final String scopeName, final String requestPath, final Pattern pattern) {
		return (NoScopeFoundException) new NoScopeFoundException().addContextValue("scopeName", scopeName).addContextValue("requestPath", requestPath).addContextValue("pattern", pattern.pattern());
	}
}
