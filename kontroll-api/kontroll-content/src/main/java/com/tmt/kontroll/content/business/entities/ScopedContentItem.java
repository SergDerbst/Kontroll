package com.tmt.kontroll.content.business.entities;

import java.sql.Timestamp;
import java.util.List;

import com.tmt.kontroll.business.annotations.BusinessEntity;
import com.tmt.kontroll.content.persistence.selections.ContentType;

@BusinessEntity(com.tmt.kontroll.content.persistence.entities.ScopedContentItem.class)
public class ScopedContentItem {

	private Integer												id;
	private Timestamp											timestamp;
	private String												tagName;
	private String												cssClass;
	private String												content;
	private ContentType										type;
	private List<ScopedContentCondition>	conditions;
	private List<ScopedContent>						scopedContents;

	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(final String tagName) {
		this.tagName = tagName;
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

	public ContentType getType() {
		return this.type;
	}

	public void setType(final ContentType type) {
		this.type = type;
	}

	public List<ScopedContentCondition> getConditions() {
		return this.conditions;
	}

	public void setConditions(final List<ScopedContentCondition> conditions) {
		this.conditions = conditions;
	}

	public List<ScopedContent> getScopedContents() {
		return this.scopedContents;
	}

	public void setScopedContents(final List<ScopedContent> scopedContents) {
		this.scopedContents = scopedContents;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(final Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
