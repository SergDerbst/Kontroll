package com.tmt.kontroll.context.ui;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmt.kontroll.context.request.data.json.DataTransferElement;

/**
 * Basic type for all DOM elements. These can be page segments, content items or
 * caption elements.
 *
 * @author SergDerbst
 *
 */
public interface DomElement extends DataTransferElement {

	@JsonProperty
	String getDomId();

	@JsonProperty
	Map<String, String> getAttributes();

	@JsonProperty
	HtmlTag getTag();
}
