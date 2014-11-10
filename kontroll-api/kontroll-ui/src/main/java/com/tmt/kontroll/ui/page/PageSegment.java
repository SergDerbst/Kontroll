package com.tmt.kontroll.ui.page;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tmt.kontroll.commons.ui.DomElement;
import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.commons.utils.annotation.EqualsHashCodeByAnnotationsBuilder;
import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentOrdinalKey;

@JsonInclude(Include.NON_EMPTY)
public abstract class PageSegment implements DomElement {

	@JsonIgnore
	private boolean																						configured;
	private String																						scope;
	private String																						parentScope;
	private HtmlTag																						tag;
	private final TreeMap<PageSegmentOrdinalKey, PageSegment>	children					= new TreeMap<>();
	private final Map<EventType, PageEvent>										generalEvents			= new EnumMap<>(EventType.class);
	private final Map<EventType, PageEvent>										additionalEvents	= new EnumMap<>(EventType.class);
	private List<ContentItem>																	content;
	private ContentItem																				caption;
	private final Map<String, String>													attributes				= new HashMap<>();
	private String																						additionalCss;

	public PageSegment() {
		this(HtmlTag.Div);
	}

	public PageSegment(final HtmlTag tag) {
		this.tag = tag;
		this.setConfigured(false);
	}

	@Override
	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getDomId() {
		return this.parentScope == null ? this.scope : this.parentScope + "." + this.scope;
	}

	@Override
	public HtmlTag getTag() {
		return this.tag;
	}

	public void setTag(final HtmlTag tag) {
		this.tag = tag;
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

	public Map<EventType, PageEvent> getGeneralEvents() {
		return this.generalEvents;
	}

	public Map<EventType, PageEvent> getAdditionalEvents() {
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

	public String getAdditionalCss() {
		return this.additionalCss;
	}

	public void setAdditionalCss(final String additionalCss) {
		this.additionalCss = additionalCss;
	}

	public boolean isConfigured() {
		return this.configured;
	}

	public void setConfigured(final boolean configured) {
		this.configured = configured;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		final PageSegment other = (PageSegment) o;
		if (this.isConfigured() && other.isConfigured()) {
			final EqualsBuilder equalsBuilder = new EqualsBuilder();
			equalsBuilder.append(this.additionalCss, other.additionalCss);
			equalsBuilder.append(this.additionalEvents, other.additionalEvents);
			equalsBuilder.append(this.attributes, other.attributes);
			equalsBuilder.append(this.caption, other.caption);
			equalsBuilder.append(this.children, other.children);
			equalsBuilder.append(this.content, other.content);
			equalsBuilder.append(this.generalEvents, other.generalEvents);
			equalsBuilder.append(this.parentScope, other.parentScope);
			equalsBuilder.append(this.scope, other.scope);
			equalsBuilder.append(this.tag, other.tag);
			return equalsBuilder.isEquals();
		}
		return EqualsHashCodeByAnnotationsBuilder.equals(this, other);
	}

	@Override
	public int hashCode() {
		if (this.isConfigured()) {
			final HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37);
			hashCodeBuilder.append(this.additionalCss);
			hashCodeBuilder.append(this.additionalEvents);
			hashCodeBuilder.append(this.attributes);
			hashCodeBuilder.append(this.caption);
			hashCodeBuilder.append(this.children);
			hashCodeBuilder.append(this.content);
			hashCodeBuilder.append(this.generalEvents);
			hashCodeBuilder.append(this.parentScope);
			hashCodeBuilder.append(this.scope);
			hashCodeBuilder.append(this.tag);
			return hashCodeBuilder.toHashCode();
		}
		return EqualsHashCodeByAnnotationsBuilder.hashCode(this);
	}
}
