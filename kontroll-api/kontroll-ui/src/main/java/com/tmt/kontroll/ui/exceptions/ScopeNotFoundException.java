package com.tmt.kontroll.ui.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class ScopeNotFoundException extends ContextedRuntimeException {

	private static final long	serialVersionUID	= -6575885134614181006L;

	private ScopeNotFoundException(final Throwable e) {
		super(e);
	}

	public static ScopeNotFoundException prepare(final Throwable e, final String scopeName) {
		return (ScopeNotFoundException) new ScopeNotFoundException(e).addContextValue("scope name", scopeName);
	}
}
