package com.tmt.kontroll.ui.page.events;

import java.util.HashMap;
import java.util.Map;

public class PageEvent {

	private final EventType						type;
	private final String[]						handlerNames;
	private final Map<String, Object>	arguments	= new HashMap<>();

	public PageEvent(	final EventType type,
	                 	final String[] handlerNames) {
		this.type = type;
		this.handlerNames = handlerNames;
	}

	public EventType getType() {
		return this.type;
	}

	public String[] getHandlerNames() {
		return this.handlerNames;
	}

	public Map<String, Object> getArguments() {
		return this.arguments;
	}
}