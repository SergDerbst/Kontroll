package com.tmt.kontroll.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ContentItem implements DomElement {

	private Integer												dbId;
	private String												domId;
	private String												css;
	private String												content;
	private ContentType										type;
	private boolean												preliminary;
	private List<ScopedContentCondition>	conditions;
	private String												itemNumber;
	private ContentChildrenOrder					contentChildrenOrder;
	private final List<ContentItem>				children		= new ArrayList<ContentItem>();
	private final Map<String, String>			attributes	= new HashMap<>();
	private final HtmlTag									tag;

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

	public ContentChildrenOrder getContentChildrenOrder() {
		return this.contentChildrenOrder;
	}

	public void setContentChildrenOrder(final ContentChildrenOrder contentChildrenOrder) {
		this.contentChildrenOrder = contentChildrenOrder;
	}

	public List<ContentItem> getChildren() {
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

	public List<ScopedContentCondition> getConditions() {
		return this.conditions;
	}

	public void setConditions(final List<ScopedContentCondition> conditions) {
		this.conditions = conditions;
	}
}
