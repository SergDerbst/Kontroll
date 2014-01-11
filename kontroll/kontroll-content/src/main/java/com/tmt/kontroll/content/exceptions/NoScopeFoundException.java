package com.tmt.kontroll.content.exceptions;

public class NoScopeFoundException extends ContentException {

	private static final long serialVersionUID = 3813948074183446602L;

	public static NoScopeFoundException prepare(final String scopeName,
	                                            final String requestContextPath) {
		return (NoScopeFoundException) new NoScopeFoundException().addContextValue("scopeName", scopeName).addContextValue("requestContextPath", requestContextPath);
	}
}
