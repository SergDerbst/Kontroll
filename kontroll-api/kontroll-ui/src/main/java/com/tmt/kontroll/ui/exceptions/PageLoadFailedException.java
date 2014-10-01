package com.tmt.kontroll.ui.exceptions;

import org.apache.commons.lang3.exception.ContextedException;

public class PageLoadFailedException extends ContextedException {

	private static final long	serialVersionUID	= -7281707021866649557L;

	public PageLoadFailedException(final Throwable e) {
		super(e);
	}
}
