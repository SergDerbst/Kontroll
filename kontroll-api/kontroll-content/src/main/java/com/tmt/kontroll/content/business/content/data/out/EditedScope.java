package com.tmt.kontroll.content.business.content.data.out;

import java.util.Set;

import com.tmt.kontroll.content.ContentItem;

/**
 * Simple DTO representing a scope for when it is being edited.
 *
 * @author SergDerbst
 *
 */
public class EditedScope {

	private final String						name;
	private final String						pattern;
	private final Set<ContentItem>	content;
	private final ContentItem				current;

	public EditedScope(	final String name,
											final String pattern,
											final Set<ContentItem> content,
											final ContentItem current) {
		this.content = content;
		this.name = name;
		this.pattern = pattern;
		this.current = current;
	}

	public String getName() {
		return this.name;
	}

	public String getPattern() {
		return this.pattern;
	}

	public Set<ContentItem> getContent() {
		return this.content;
	}

	public ContentItem getCurrent() {
		return this.current;
	}
}
