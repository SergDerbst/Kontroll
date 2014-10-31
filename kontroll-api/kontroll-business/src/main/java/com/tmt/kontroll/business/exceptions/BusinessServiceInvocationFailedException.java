package com.tmt.kontroll.business.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class BusinessServiceInvocationFailedException extends ContextedRuntimeException {

	private static final long	serialVersionUID	= -8321777205914106812L;

	public BusinessServiceInvocationFailedException(final Throwable cause) {
		super(cause);
	}

}
