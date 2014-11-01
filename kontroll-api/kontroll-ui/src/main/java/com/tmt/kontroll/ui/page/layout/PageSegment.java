package com.tmt.kontroll.ui.page.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tmt.kontroll.commons.ui.DomElement;
import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentOrdinalKey;

@JsonInclude(Include.NON_EMPTY)
public abstract class PageSegment implements DomElement {

	private String																						scope;
	private String																						parentScope;
	private final HtmlTag																			tag;
	private final TreeMap<PageSegmentOrdinalKey, PageSegment>	children					= new TreeMap<>();
	private final List<PageEvent>															generalEvents			= new ArrayList<>();
	private final List<PageEvent>															additionalEvents	= new ArrayList<>();
	private List<ContentItem>																	content;
	private ContentItem																				caption;
	private final Map<String, String>													attributes				= new HashMap<>();

	public PageSegment() {
		this.tag = HtmlTag.Div;
	}

	public PageSegment(final HtmlTag tag) {
		this.tag = tag;
	}

	@Override
	public HtmlTag getTag() {
		return this.tag;
	}

	@Override
	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getDomId() {
		return this.parentScope == null ? this.scope : this.parentScope + "." + this.scope;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(final String scope) {
		this.scope = scope;
	}

	public String getParentScope() {
		return this.parentScope;
	}

	public void setParentScope(final String parentScope) {
		this.parentScope = parentScope;
	}

	public TreeMap<PageSegmentOrdinalKey, PageSegment> getChildren() {
		return this.children;
	}

	public boolean hasChildren() {
		return this.children.size() > 0;
	}

	public List<PageEvent> getGeneralEvents() {
		return this.generalEvents;
	}

	public List<PageEvent> getAdditionalEvents() {
		return this.additionalEvents;
	}

	public List<ContentItem> getContent() {
		return this.content;
	}

	public void setContent(final List<ContentItem> content) {
		this.content = content;
	}

	public ContentItem getCaption() {
		return this.caption;
	}

	public void setCaption(final ContentItem caption) {
		this.caption = caption;
	}
}
