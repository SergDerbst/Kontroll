package com.tmt.kontroll.ui.page.configuration.enums.components;

import com.fasterxml.jackson.annotation.JsonValue;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.textarea.Textarea;

/**
 * Configuration enum for {@link Textarea}. It determines the value of the wrap attribute.
 *
 * @author SergDerbst
 *
 */
public enum WrapType {
	Off("off"),
	Virtual("virtual"),
	Physical("physical");
	private final String	value;

	private WrapType(final String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return this.value;
	}
}