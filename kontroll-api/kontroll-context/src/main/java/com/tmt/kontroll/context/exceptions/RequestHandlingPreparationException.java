package com.tmt.kontroll.context.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

import com.tmt.kontroll.context.request.handling.RequestHandling;

public class RequestHandlingPreparationException extends ContextedRuntimeException {

	private static final long	serialVersionUID	= 2535760027999559007L;

	private RequestHandlingPreparationException(final String message) {
		super(message);
	}

	public static RequestHandlingPreparationException prepare(final RequestHandling requestHandling) {
		return (RequestHandlingPreparationException) new RequestHandlingPreparationException("Failed to prepare handling queue").addContextValue("requestHandling", requestHandling);
	}
}
