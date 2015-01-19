package com.tmt.kontroll.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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

	private String										id;
	private String										cssClass;
	private String										itemNumber;
	private ContentType								contentType;
	private String										content;
	private ContentChildrenOrder			contentChildrenOrder;
	private final List<ContentItem>		children		= new ArrayList<ContentItem>();
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

	@Override
	public String getDomId() {
		return this.id;
	}

	@Override
	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	@Override
	public HtmlTag getTag() {
		return this.tag;
	}

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getCssClass() {
		return this.cssClass;
	}

	public void setCssClass(final String cssClass) {
		this.cssClass = cssClass;
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

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}
}
