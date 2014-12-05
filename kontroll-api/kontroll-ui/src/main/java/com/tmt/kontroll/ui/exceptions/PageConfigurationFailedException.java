package com.tmt.kontroll.ui.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

import com.tmt.kontroll.ui.page.segments.PageSegment;

public class PageConfigurationFailedException extends ContextedRuntimeException {

	private static final long	serialVersionUID	= 7784858117815426581L;

	private PageConfigurationFailedException(final String message) {
		super(message);
	}

	protected PageConfigurationFailedException() {
		super();
	}

	public PageConfigurationFailedException(final Throwable cause) {
		super(cause);
	}

	public static PageConfigurationFailedException prepareValidity(final String message, final PageSegment segment) {
		return (PageConfigurationFailedException) new PageConfigurationFailedException(message).addContextValue("page segment class", segment);
	}
}
