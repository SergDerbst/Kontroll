package com.tmt.kontroll.content.exceptions;

import com.tmt.kontroll.content.business.content.data.ContentLoadingContext;

public class NoContentFoundException extends ContentException {

	private static final long serialVersionUID = 5832275862405538812L;

	public static NoContentFoundException prepare(final ContentLoadingContext dto) {
		return (NoContentFoundException) new NoContentFoundException().addContextValue("contentDTO", dto);
	}
}
