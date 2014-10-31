package com.tmt.kontroll.ui.page.events;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum representing events that can occur on the DOM element of a page.
 *
 * @author SergDerbst
 *
 */
public enum EventType {
	Click("click");

	private String	name;

	private EventType(final String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}
}
