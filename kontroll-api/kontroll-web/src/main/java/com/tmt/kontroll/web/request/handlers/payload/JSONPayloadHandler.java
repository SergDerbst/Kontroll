package com.tmt.kontroll.web.request.handlers.payload;

import java.io.BufferedReader;

import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmt.kontroll.context.annotations.RequestHandler;
import com.tmt.kontroll.context.exceptions.RequestHandlingException;
import com.tmt.kontroll.context.request.data.json.RequestDto;
import com.tmt.kontroll.context.request.handling.RequestHandling;
import com.tmt.kontroll.context.request.handling.RequestHandlingParam;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;
import com.tmt.kontroll.web.request.handlers.session.SessionContextHandler;

@RequestHandler(handling = RequestHandling.AlwaysFirst, preHandling = {SessionContextHandler.class}, excludedMethod = {RequestMethod.GET})
public class JSONPayloadHandler implements RequestHandlingService {

	private final JsonFactory		jsonFactory		= new JsonFactory();
	private final ObjectMapper	objectMapper	= new ObjectMapper(this.jsonFactory);

	@Override
	public void handle(final RequestHandlingParam param) {
		param.setPayload(this.parseJSON(param, this.readPayload(param)));
	}

	private RequestDto parseJSON(final RequestHandlingParam param, final String payload) {
		try {
			return this.objectMapper.readValue(payload, RequestDto.class);
		} catch (final Exception e) {
			throw new RequestHandlingException(e);
		}
	}

	private String readPayload(final RequestHandlingParam param) {
		final StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = param.getRequest().getReader();
			final char[] buffer = new char[128];
			int bytesRead = -1;
			while ((bytesRead = reader.read(buffer)) > 0) {
				builder.append(buffer, 0, bytesRead);
			}
		} catch (final Exception e) {
			throw new RequestHandlingException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (final Exception e) {
					throw new RequestHandlingException(e);
				}
			}
		}
		return builder.toString();
	}
}
