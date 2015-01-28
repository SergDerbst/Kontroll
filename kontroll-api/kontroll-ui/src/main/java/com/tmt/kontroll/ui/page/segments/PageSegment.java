package com.tmt.kontroll.ui.page.segments;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.context.ui.DomElement;
import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentOrdinalKey;

/**
 * A page segment is the basic object from which a whole page is to be constructed. Essentially,
 * it represents a DOM element. It contains all values necessary for generating the element in question.
 *
 * @author SergDerbst
 *
 */
public abstract class PageSegment implements DomElement {

	@JsonIgnore
	private boolean																						configured;
	@JsonIgnore
	private String																						captionIdentifier;

	private final List<String>																requestPatterns		= new ArrayList<>();
	private String																						scope;
	private String																						parentScope;
	private HtmlTag																						tag;
	private int																								ordinal;
	@JsonProperty
	private final List<PageSegment>														topChildren				= new ArrayList<>();
	@JsonProperty
	private final List<PageSegment>														bottomChildren		= new ArrayList<>();
	@JsonProperty
	private final TreeMap<PageSegmentOrdinalKey, PageSegment>	mainChildren			= new TreeMap<>();
	@JsonProperty
	private Set<ContentItem>																	content;
	private ContentItem																				caption;
	private final Map<EventType, PageEvent>										generalEvents			= new EnumMap<>(EventType.class);
	private final Map<EventType, PageEvent>										additionalEvents	= new EnumMap<>(EventType.class);
	private final Map<String, String>													attributes				= new HashMap<>();
	private final Map<String, String>													configOptions			= new HashMap<>();

	public PageSegment() {
		this(HtmlTag.Div);
	}

	public PageSegment(final HtmlTag tag) {
		this.tag = tag;
		this.setConfigured(false);
	}

	@Override
	public String getDtoClass() {
		return this.getClass().getName();
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

	public Map<EventType, PageEvent> getGeneralEvents() {
		return this.generalEvents;
	}

	public Map<EventType, PageEvent> getAdditionalEvents() {
		return this.additionalEvents;
	}

	protected boolean containsChild(final PageSegment child) {
		return this.mainChildren.containsValue(child) || this.topChildren.contains(child) || this.bottomChildren.contains(child);
	}

	protected Map<PageSegmentOrdinalKey, PageSegment> getMainChildren() {
		return this.mainChildren;
	}

	protected List<PageSegment> getTopChildren() {
		return this.topChildren;
	}

	protected List<PageSegment> getBottomChildren() {
		return this.bottomChildren;
	}

	protected boolean hasChildren() {
		return !(this.mainChildren.isEmpty() && this.topChildren.isEmpty() && this.bottomChildren.isEmpty());
	}

	protected Set<ContentItem> getContent() {
		return this.content;
	}

	protected void setContent(final Set<ContentItem> content) {
		this.content = content;
	}

	public ContentItem getCaption() {
		return this.caption;
	}

	public void setCaption(final ContentItem caption) {
		this.caption = caption;
	}

	public boolean isConfigured() {
		return this.configured;
	}

	public void setConfigured(final boolean configured) {
		this.configured = configured;
	}

	public int getOrdinal() {
		return this.ordinal;
	}

	public void setOrdinal(final int ordinal) {
		this.ordinal = ordinal;
	}

	public List<String> getRequestPatterns() {
		return this.requestPatterns;
	}

	public String getCaptionIdentifier() {
		return this.captionIdentifier;
	}

	public void setCaptionIdentifier(final String captionIdentifier) {
		this.captionIdentifier = captionIdentifier;
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
		final EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(this.parentScope, other.parentScope);
		equalsBuilder.append(this.scope, other.scope);
		equalsBuilder.append(this.requestPatterns, other.requestPatterns);
		equalsBuilder.append(this.additionalEvents, other.additionalEvents);
		equalsBuilder.append(this.attributes, other.attributes);
		equalsBuilder.append(this.bottomChildren, other.bottomChildren);
		equalsBuilder.append(this.caption, other.caption);
		equalsBuilder.append(this.captionIdentifier, other.captionIdentifier);
		equalsBuilder.append(this.configOptions, other.configOptions);
		equalsBuilder.append(this.configured, other.configured);
		equalsBuilder.append(this.content, other.content);
		equalsBuilder.append(this.generalEvents, other.generalEvents);
		equalsBuilder.append(this.mainChildren, other.mainChildren);
		equalsBuilder.append(this.ordinal, other.ordinal);
		equalsBuilder.append(this.tag, other.tag);
		equalsBuilder.append(this.topChildren, other.topChildren);
		return equalsBuilder.isEquals();
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37);
		hashCodeBuilder.append(this.parentScope);
		hashCodeBuilder.append(this.scope);
		hashCodeBuilder.append(this.requestPatterns);
		hashCodeBuilder.append(this.additionalEvents);
		hashCodeBuilder.append(this.attributes);
		hashCodeBuilder.append(this.bottomChildren);
		hashCodeBuilder.append(this.caption);
		hashCodeBuilder.append(this.captionIdentifier);
		hashCodeBuilder.append(this.configOptions);
		hashCodeBuilder.append(this.configured);
		hashCodeBuilder.append(this.content);
		hashCodeBuilder.append(this.generalEvents);
		hashCodeBuilder.append(this.mainChildren);
		hashCodeBuilder.append(this.ordinal);
		hashCodeBuilder.append(this.tag);
		hashCodeBuilder.append(this.topChildren);
		return hashCodeBuilder.toHashCode();
	}

	public Map<String, String> getConfigOptions() {
		return this.configOptions;
	}
}
