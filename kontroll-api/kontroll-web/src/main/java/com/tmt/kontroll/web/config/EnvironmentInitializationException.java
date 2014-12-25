package com.tmt.kontroll.web.config;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class EnvironmentInitializationException extends ContextedRuntimeException {

	private static final long	serialVersionUID	= -459714541632586731L;

	private EnvironmentInitializationException() {
		super();
	}

	private EnvironmentInitializationException(final Throwable e) {
		super(e);
	}

	public static EnvironmentInitializationException prepare(final Map<String, String> variables) {
		return prepare(null, variables);
	}

	public static EnvironmentInitializationException prepare(final Throwable cause, final Map<String, String> variables) {
		final EnvironmentInitializationException e = cause == null ? new EnvironmentInitializationException() : new EnvironmentInitializationException(cause);
		for (final Entry<String, String> v : variables.entrySet()) {
			e.addContextValue(v.getKey(), v.getValue());
		}
		return e;
	}
}
