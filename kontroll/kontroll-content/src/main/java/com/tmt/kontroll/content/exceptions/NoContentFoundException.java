package com.tmt.kontroll.content.exceptions;

import com.tmt.kontroll.content.ContentDto;

public class NoContentFoundException extends ContentException {

	private static final long serialVersionUID = 5832275862405538812L;

	public static NoContentFoundException prepare(final ContentDto dto) {
		return (NoContentFoundException) new NoContentFoundException().addContextValue("contentDTO", dto);
	}
}
