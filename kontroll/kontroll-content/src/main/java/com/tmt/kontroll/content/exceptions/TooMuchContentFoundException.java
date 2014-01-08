package com.tmt.kontroll.content.exceptions;

import com.tmt.kontroll.content.ContentDto;

public class TooMuchContentFoundException extends ContentException {

	private static final long serialVersionUID = 2317681894869769839L;

	public static TooMuchContentFoundException prepare(final ContentDto dto) {
		return (TooMuchContentFoundException) new TooMuchContentFoundException().addContextValue("contentDto", dto);
	}
}
