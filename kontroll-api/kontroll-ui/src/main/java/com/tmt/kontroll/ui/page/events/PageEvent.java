package com.tmt.kontroll.ui.page.events;

import java.util.ArrayList;
import java.util.List;

public class PageEvent {

	private final EventType			type;
	private final String				eventHandlerName;
	private final List<Object>	arguments	= new ArrayList<>();

	public PageEvent(	final EventType type,
										final String eventHandlerName) {
		this.type = type;
		this.eventHandlerName = eventHandlerName;
	}

	public EventType getType() {
		return this.type;
	}

	public String getEventHandlerName() {
		return this.eventHandlerName;
	}

	public List<Object> getArguments() {
		return this.arguments;
	}
}