package com.tmt.kontroll.test.persistence.run.utils.exceptions;

@SuppressWarnings("serial")
public class EntityClassNotFoundException extends RuntimeException{

	public EntityClassNotFoundException(final String message) {
		super(message);
	}

	public EntityClassNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
