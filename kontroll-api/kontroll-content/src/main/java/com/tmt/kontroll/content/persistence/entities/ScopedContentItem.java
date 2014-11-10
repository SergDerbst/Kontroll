package com.tmt.kontroll.content.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.IndexColumn;

import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.persistence.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
public class ScopedContentItem extends BaseEntity {

	@Column(columnDefinition = "varChar(12) default 'div'")
	private String												tagName;

	@Column(length = DatabaseDefinitions.String_MediumSmall)
	private String												cssClass;

	@Column(nullable = false, length = DatabaseDefinitions.String_XLarge)
	private String												content;

	@Enumerated(EnumType.STRING)
	private ContentType										type;

	@ManyToMany(mappedBy = "scopedContentItems", fetch = FetchType.EAGER)
	@IndexColumn(name = "id")
	private List<ScopedContentCondition>	conditions;

	@ManyToMany(mappedBy = "scopedContentItems", fetch = FetchType.EAGER)
	@IndexColumn(name = "id")
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
}
