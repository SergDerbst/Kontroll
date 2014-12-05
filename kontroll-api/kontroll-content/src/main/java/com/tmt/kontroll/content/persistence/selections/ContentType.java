package com.tmt.kontroll.content.persistence.selections;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ContentType {

	Audio,
	Image,
	Text,
	Video;

	@JsonValue
	public String getName() {
		return this.name();
	}
}
