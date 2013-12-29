package com.tmt.kontroll.content.items;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.content.persistence.selections.ContentChildrenOrder;

/**
 * @author Serg Derbst
 * 
 * @param <T>
 *          Type of Enum that represents the tag of this content item.
 */
public abstract class ContentItem<T extends Enum<?>> {

	private String id;
	private String cssClass;
	private String content;
	private ContentChildrenOrder contentChildrenOrder;
	private final List<ContentItem<? extends Enum<?>>> children = new ArrayList<ContentItem<? extends Enum<?>>>();
	
	public abstract T getTag();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ContentChildrenOrder getContentChildrenOrder() {
		return contentChildrenOrder;
	}

	public void setContentChildrenOrder(ContentChildrenOrder contentChildrenOrder) {
		this.contentChildrenOrder = contentChildrenOrder;
	}

	public List<ContentItem<? extends Enum<?>>> getChildren() {
		return children;
	}
}
