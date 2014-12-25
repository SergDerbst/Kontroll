package com.tmt.kontroll.content.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.IndexColumn;

import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.persistence.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;
import com.tmt.kontroll.security.persistence.entities.UserAccount;

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

	@Column(nullable = false)
	private boolean												preliminary;

	@ManyToOne
	private UserAccount										lastEdited;

	@Column(nullable = false, length = DatabaseDefinitions.String_Small)
	private String												itemNumber;

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

	public boolean isPreliminary() {
		return this.preliminary;
	}

	public void setPreliminary(final boolean preliminary) {
		this.preliminary = preliminary;
	}

	public UserAccount getLastEdited() {
		return this.lastEdited;
	}

	public void setLastEdited(final UserAccount lastEdited) {
		this.lastEdited = lastEdited;
	}

	public String getItemNumber() {
		return this.itemNumber;
	}

	public void setItemNumber(final String itemNumber) {
		this.itemNumber = itemNumber;
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
