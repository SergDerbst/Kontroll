package com.tmt.kontroll.content.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;

import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.persistence.entities.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
public class ScopedContentItem extends BaseEntity {

	@Column(columnDefinition = "varChar(12) default 'div'")
	private String tagName;

	@Column(length = DatabaseDefinitions.String_MediumSmall)
	private String cssClass;

	@Column(nullable = false, length = DatabaseDefinitions.String_XLarge)
	private String					content;

	@Enumerated(EnumType.STRING)
	private ContentType			type;

	@ManyToMany(mappedBy = "scopedContentItems")
	private List<ScopedContentCondition>	conditions;
	
	@ManyToMany(mappedBy = "scopedContentItems")
	private List<ScopedContent>	scopedContents;

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
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

	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}

	public List<ScopedContentCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<ScopedContentCondition> conditions) {
		this.conditions = conditions;
	}

	public List<ScopedContent> getScopedContents() {
		return scopedContents;
	}

	public void setScopedContents(List<ScopedContent> scopedContents) {
		this.scopedContents = scopedContents;
	}
}
