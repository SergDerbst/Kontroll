package com.tmt.kontroll.ui.exceptions;

import org.apache.commons.lang3.exception.ContextedException;

public class PageNotFoundException extends ContextedException {

	private static final long	serialVersionUID	= 4503755749474229467L;

	public static PageNotFoundException prepare(final String requestPath) {
		return (PageNotFoundException) new PageNotFoundException().addContextValue("request path", requestPath);
	}
}
