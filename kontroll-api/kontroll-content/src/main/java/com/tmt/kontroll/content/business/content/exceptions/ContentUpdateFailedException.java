package com.tmt.kontroll.content.business.content.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class ContentUpdateFailedException extends ContextedRuntimeException {

	private static final long	serialVersionUID	= 1L;

	public ContentUpdateFailedException(final Throwable cause) {
		super(cause);
	}
}
