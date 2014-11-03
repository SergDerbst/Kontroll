package com.tmt.kontroll.ui.page.events;

import java.util.HashMap;
import java.util.Map;

public class PageEvent {

	private final EventType						type;
	private final String							handlerName;
	private final Map<String, Object>	arguments	= new HashMap<>();

	public PageEvent(	final EventType type,
										final String handlerName) {
		this.type = type;
		this.handlerName = handlerName;
	}

	public EventType getType() {
		return this.type;
	}

	public String getHandlerName() {
		return this.handlerName;
	}

	public Map<String, Object> getArguments() {
		return this.arguments;
	}
}