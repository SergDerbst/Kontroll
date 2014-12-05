package com.tmt.kontroll.context.request.data.json;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class DataTransferElementDeserializationException extends ContextedRuntimeException {

	private static final long	serialVersionUID	= -4752687206039624721L;

	public DataTransferElementDeserializationException(final Throwable cause) {
		super(cause);
	}
}
