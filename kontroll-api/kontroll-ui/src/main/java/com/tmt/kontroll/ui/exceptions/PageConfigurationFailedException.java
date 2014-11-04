package com.tmt.kontroll.ui.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class PageConfigurationFailedException extends ContextedRuntimeException {

	private static final long	serialVersionUID	= 7784858117815426581L;

	public PageConfigurationFailedException(final Throwable cause) {
		super(cause);
	}
}
