package com.tmt.kontroll.context.request.data.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Basic type for all DTOs of Kontroll that get are used as transfer elements
 * to be serialized to JSON objects and sent back to the client.
 *
 * @author SergDerbst
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonDeserialize(using = DataTransferElementDeserializer.class)
public interface DataTransferElement {

	@JsonProperty("_dtoClass")
	String getDtoClass();
}