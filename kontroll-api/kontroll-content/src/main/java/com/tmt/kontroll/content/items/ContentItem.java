package com.tmt.kontroll.content.items;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tmt.kontroll.content.persistence.selections.ContentChildrenOrder;

/**
 * @author Sergio Weigel
 *
 * @param <T>
 *          Type of Enum that represents the tag of this content item.
 */
@JsonInclude(Include.NON_EMPTY)
public abstract class ContentItem<T extends Enum<?>> {

	private String																			id;
	private String																			cssClass;
	private String																			content;
	private ContentChildrenOrder												contentChildrenOrder;
	private final List<ContentItem<? extends Enum<?>>>	children	= new ArrayList<ContentItem<? extends Enum<?>>>();

	public abstract T getTag();

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

	public List<ContentItem<? extends Enum<?>>> getChildren() {
		return this.children;
	}
}
