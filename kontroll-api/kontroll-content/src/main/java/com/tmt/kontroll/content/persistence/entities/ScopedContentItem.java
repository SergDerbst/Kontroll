package com.tmt.kontroll.content.persistence.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.persistence.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;
import com.tmt.kontroll.security.persistence.entities.UserAccount;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_item_state", columnNames = {"content", "condition", "itemnumber", "preliminary", "deleted"})})
public class ScopedContentItem extends BaseEntity implements Comparable<ScopedContentItem> {

	@Column(nullable = false, length = DatabaseDefinitions.String_Small)
	private String									itemNumber;

	@Column(columnDefinition = "varChar(12) default 'div'")
	@Enumerated(EnumType.STRING)
	private HtmlTag									tag;

	@Column(nullable = false, length = DatabaseDefinitions.String_XLarge)
	private String									content;

	@Column(length = DatabaseDefinitions.String_MediumSmall)
	private String									css;

	@Enumerated(EnumType.STRING)
	private ContentType							type;

	@Column(nullable = false)
	private boolean									preliminary;

	@Column(nullable = false)
	private boolean									deleted;

	@ManyToOne
	private UserAccount							lastEdited;

	@ManyToMany(mappedBy = "scopedContentItems")
	private Set<Scope>							scopes;

	@ManyToOne
	@JoinColumn(name = "condition")
	private ScopedContentCondition	condition;

	@ManyToMany
	@JoinTable(name = "NestedScopedContentItems", joinColumns = @JoinColumn(name = "parent_item_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "child_item_id", referencedColumnName = "id"), uniqueConstraints = {@UniqueConstraint(name = "unique_item_nesting", columnNames = {"parent_item_id", "child_item_id"})})
	private Set<ScopedContentItem>	parentItems;

	@ManyToMany(mappedBy = "parentItems")
	private Set<ScopedContentItem>	childItems;

	public HtmlTag getTag() {
		return this.tag;
	}

	public void setTag(final HtmlTag tag) {
		this.tag = tag;
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

	public Set<Scope> getScopes() {
		return this.scopes;
	}

	public void setScopes(final Set<Scope> scopes) {
		this.scopes = scopes;
	}

	public ScopedContentCondition getCondition() {
		return this.condition;
	}

	public void setCondition(final ScopedContentCondition condition) {
		this.condition = condition;
	}

	public Set<ScopedContentItem> getParentItems() {
		return this.parentItems;
	}

	public void setParentItems(final Set<ScopedContentItem> parentItems) {
		this.parentItems = parentItems;
	}

	public Set<ScopedContentItem> getChildItems() {
		return this.childItems;
	}

	public void setChildItems(final Set<ScopedContentItem> childItems) {
		this.childItems = childItems;
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
		final ScopedContentItem other = (ScopedContentItem) o;
		final EqualsBuilder equals = new EqualsBuilder();
		equals.append(this.condition, other.condition);
		equals.append(this.content, other.content);
		equals.append(this.css, other.css);
		equals.append(this.deleted, other.deleted);
		equals.append(this.itemNumber, other.itemNumber);
		equals.append(this.lastEdited, other.lastEdited);
		equals.append(this.preliminary, other.preliminary);
		equals.append(this.tag, other.tag);
		equals.append(this.type, other.type);
		equals.append(this.childItems, other.childItems);
		return equals.build();
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hashCode = new HashCodeBuilder(17, 37);
		hashCode.append(this.condition);
		hashCode.append(this.content);
		hashCode.append(this.css);
		hashCode.append(this.deleted);
		hashCode.append(this.itemNumber);
		hashCode.append(this.lastEdited);
		hashCode.append(this.preliminary);
		hashCode.append(this.tag);
		hashCode.append(this.type);
		hashCode.append(this.childItems);
		return hashCode.build();
	}

	@Override
	public int compareTo(final ScopedContentItem other) {
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
