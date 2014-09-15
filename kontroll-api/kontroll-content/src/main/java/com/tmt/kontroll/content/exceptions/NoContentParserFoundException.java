package com.tmt.kontroll.content.exceptions;

import com.tmt.kontroll.content.persistence.selections.ContentType;

public class NoContentParserFoundException extends ContentException {

	private static final long serialVersionUID = 3202196518333887320L;

	public static NoContentParserFoundException prepare(final ContentType type) {
		return (NoContentParserFoundException) new NoContentParserFoundException().addContextValue("contentType", type);
	}
}
