package com.tmt.kontroll.commons.ui;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Basic type for all DOM elements. These can be page segments, content items or
 * caption elements.
 *
 * @author SergDerbst
 *
 */
public interface DomElement {

	@JsonProperty
	String getDomId();

	@JsonProperty
	Map<String, String> getAttributes();

	@JsonProperty
	HtmlTag getTag();

}
