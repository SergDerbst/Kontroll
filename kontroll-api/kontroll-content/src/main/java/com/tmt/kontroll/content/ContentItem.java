package com.tmt.kontroll.content;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.selections.ContentChildrenOrder;
import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.context.ui.DomElement;
import com.tmt.kontroll.context.ui.HtmlTag;

/**
 * Business object.
 *
 * @author SergDerbst
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class ContentItem implements DomElement, Comparable<ContentItem> {

	private Integer										dbId;
	private String										domId;
	private String										css;
	private String										content;
	private ContentType								type;
	private boolean										preliminary;
	private boolean										deleted;
	private ScopedContentCondition		condition;
	private String										itemNumber;
	private ContentChildrenOrder			contentChildrenOrder;
	private final Set<ContentItem>		children		= new TreeSet<>();
	private final Map<String, String>	attributes	= new HashMap<>();
	private final HtmlTag							tag;

	public ContentItem() {
		this.tag = HtmlTag.Div;
	}

	public ContentItem(final HtmlTag tag) {
		this.tag = tag;
	}

	@Override
	public String getDtoClass() {
		return this.getClass().getName();
	}

	public Integer getDbId() {
		return this.dbId;
	}

	public void setDbId(final Integer dbId) {
		this.dbId = dbId;
	}

	@Override
	public String getDomId() {
		return this.domId;
	}

	public void setDomId(final String domId) {
		this.domId = domId;
	}

	@Override
	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	@Override
	public HtmlTag getTag() {
		return this.tag;
	}

	public String getCss() {
		return this.css;
	}

	public void setCss(final String css) {
		this.css = css;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public ScopedContentCondition getCondition() {
		return this.condition;
	}

	public void setCondition(final ScopedContentCondition condition) {
		this.condition = condition;
	}

	public ContentChildrenOrder getContentChildrenOrder() {
		return this.contentChildrenOrder;
	}

	public void setContentChildrenOrder(final ContentChildrenOrder contentChildrenOrder) {
		this.contentChildrenOrder = contentChildrenOrder;
	}

	public Set<ContentItem> getChildren() {
		return this.children;
	}

	public String getItemNumber() {
		return this.itemNumber;
	}

	public void setItemNumber(final String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public ContentType getType() {
		return this.type;
	}

	public void setType(final ContentType type) {
		this.type = type;
	}

	public boolean isPreliminary() {
		return this.preliminary;
	}

	public void setPreliminary(final boolean preliminary) {
		this.preliminary = preliminary;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(final boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (!this.getClass().equals(o.getClass())) {
			return false;
		}
		final ContentItem other = (ContentItem) o;
		final EqualsBuilder equals = new EqualsBuilder();
		equals.append(this.condition, other.condition);
		equals.append(this.content, other.content);
		equals.append(this.css, other.css);
		equals.append(this.itemNumber, other.itemNumber);
		equals.append(this.preliminary, other.preliminary);
		equals.append(this.tag, other.tag);
		equals.append(this.type, other.type);
		return equals.build();
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hashCode = new HashCodeBuilder(17, 37);
		hashCode.append(this.condition);
		hashCode.append(this.content);
		hashCode.append(this.css);
		hashCode.append(this.itemNumber);
		hashCode.append(this.preliminary);
		hashCode.append(this.tag);
		hashCode.append(this.type);
		return hashCode.build();
	}

	@Override
	public int compareTo(final ContentItem other) {
		if (this.equals(other)) {
			return 0;
		}
		final int itemNumberComparison = this.itemNumber.compareTo(other.itemNumber);
		if (itemNumberComparison != 0) {
			return itemNumberComparison;
		}
		final int conditionComparison = this.condition.hashCode() - other.condition.hashCode();
		if (conditionComparison != 0) {
			return conditionComparison;
		}
		return this.hashCode() - other.hashCode();
	}
}
