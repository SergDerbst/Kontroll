package com.tmt.kontroll.ui.page.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Holds additional trigger events by a {@link PageSegment}s full scope name.
 *
 * @author SergDerbst
 *
 */
@Component
public class TriggerEventHolder {

	private final Map<String, List<PageEvent>>	scopeTriggerMap	= new HashMap<>();

	public void add(final String scopeName, final PageEvent event) {
		List<PageEvent> events = this.scopeTriggerMap.get(scopeName);
		if (events == null) {
			events = new ArrayList<PageEvent>();
			this.scopeTriggerMap.put(scopeName, events);
		}
		events.add(event);
	}

	public List<PageEvent> get(final String scopeName) {
		return this.scopeTriggerMap.get(scopeName);
	}
}
