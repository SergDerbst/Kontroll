package com.tmt.kontroll.content.business.caption;

import java.util.Locale;

public class CaptionDto {

	private final Locale	locale;
	private final String	identifier;

	public CaptionDto(final Locale locale,
										final String identifier) {
		this.locale = locale;
		this.identifier = identifier;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public String getIdentifier() {
		return this.identifier;
	}
}
