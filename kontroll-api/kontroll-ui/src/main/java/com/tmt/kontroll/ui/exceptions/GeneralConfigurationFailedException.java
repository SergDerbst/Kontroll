package com.tmt.kontroll.ui.exceptions;

import com.tmt.kontroll.ui.page.PageSegment;

public class GeneralConfigurationFailedException extends PageConfigurationFailedException {

	private static final long	serialVersionUID	= 1L;

	private GeneralConfigurationFailedException() {
		super();
	}

	private GeneralConfigurationFailedException(final Throwable cause) {
		super(cause);
	}

	public static GeneralConfigurationFailedException prepare(final PageSegment segment, final Throwable cause) {
		return (GeneralConfigurationFailedException) new GeneralConfigurationFailedException(cause).addContextValue("segment class", segment.getClass());
	}

	public static GeneralConfigurationFailedException prepare(final PageSegment segment) {
		return (GeneralConfigurationFailedException) new GeneralConfigurationFailedException().addContextValue("segment class", segment.getClass().getName());
	}
}
