package com.tmt.kontroll.context.request.data.json;

import java.io.IOException;
import java.lang.reflect.Field;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils;

/**
 * Custom Kontroll deserializer to handle {@link DataTransferElement} properly.
 *
 * @author SergDerbst
 *
 */
public class DataTransferElementDeserializer extends JsonDeserializer<DataTransferElement> {

	private final JsonFactory		jsonFactory		= new JsonFactory();
	private final ObjectMapper	objectMapper	= new ObjectMapper(this.jsonFactory);

	@SuppressWarnings("unchecked")
	@Override
	public DataTransferElement deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			final JsonNode node = jp.getCodec().readTree(jp);
			final Class<? extends DataTransferElement> dtoClass = (Class<? extends DataTransferElement>) ctxt.findClass(node.get("_dtoClass").textValue());
			final DataTransferElement dto = dtoClass.newInstance();
			for (final Field field : ClassReflectionUtils.retrievePropertyFields(dtoClass)) {
				final JsonNode fieldNode = node.get(field.getName());
				if (fieldNode != null) {
					final Class<?> fieldType = field.getType();
					field.setAccessible(true);
					field.set(dto, this.objectMapper.treeToValue(fieldNode, fieldType));
				}
			}
			return dto;
		} catch (final Exception e) {
			throw new DataTransferElementDeserializationException(e);
		}
	}
}