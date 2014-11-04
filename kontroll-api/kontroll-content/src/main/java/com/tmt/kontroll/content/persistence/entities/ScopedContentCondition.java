package com.tmt.kontroll.content.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.persistence.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
public class ScopedContentCondition extends BaseEntity {

	@Column(nullable = false, length = DatabaseDefinitions.String_Small)
	private String							name;

	@Column(length = DatabaseDefinitions.String_Large)
	private String							description;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ConditionalOperator	operator;

	@ManyToMany
	@JoinTable(name = "ScopedContentConditions_ScopedContent",
	joinColumns = @JoinColumn(name = "scoped_content_condition_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "scoped_content_id", referencedColumnName = "id"),
	uniqueConstraints = { @UniqueConstraint(name = "unique_scope_item",
	columnNames = { "scoped_content_condition_id", "scoped_content_id" }) })
	private List<ScopedContent>	scopedContents;

	@ManyToMany
	@JoinTable(name = "ScopedContentConditions_ScopedContentItem",
	joinColumns = @JoinColumn(name = "scoped_content_condition_id",
	referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(	name = "scoped_content_item_id",
	referencedColumnName = "id"),
	uniqueConstraints = { @UniqueConstraint(name = "unique_scope_item",
	columnNames = { "scoped_content_condition_id", "scoped_content_item_id" }) })
	private List<ScopedContentItem>	scopedContentItems;

	@OneToMany(mappedBy = "scopedContentCondition")
	private List<ScopedContentConditionAttribute> scopedContentConditionAttributes;

	@ManyToMany
	@JoinTable(name = "NestedScopedContentConditions",
	joinColumns = @JoinColumn(name = "parent_condition_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "child_condition_id", referencedColumnName = "id"),
	uniqueConstraints = { @UniqueConstraint(name = "unique_condition_nesting",
	columnNames = { "parent_condition_id", "child_condition_id" }) })
	private List<ScopedContentCondition> parentConditions;

	@ManyToMany(mappedBy = "parentConditions")
	private List<ScopedContentCondition> childConditions;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public ConditionalOperator getOperator() {
		return this.operator;
	}

	public void setOperator(final ConditionalOperator operator) {
		this.operator = operator;
	}

	public List<ScopedContent> getScopedContents() {
		return this.scopedContents;
	}

	public void setScopedContents(final List<ScopedContent> scopedContents) {
		this.scopedContents = scopedContents;
	}

	public List<ScopedContentItem> getScopedContentItems() {
		return this.scopedContentItems;
	}

	public void setScopedContentItems(final List<ScopedContentItem> scopedContentItems) {
		this.scopedContentItems = scopedContentItems;
	}

	public List<ScopedContentConditionAttribute> getConditionAttributes() {
		return this.scopedContentConditionAttributes;
	}

	public void setScopedContentConditionAttributes(final List<ScopedContentConditionAttribute> scopedContentConditionAttributes) {
		this.scopedContentConditionAttributes = scopedContentConditionAttributes;
	}

	public List<ScopedContentConditionAttribute> getScopedContentConditionAttributes() {
		return this.scopedContentConditionAttributes;
	}

	public List<ScopedContentCondition> getChildConditions() {
		return this.childConditions;
	}

	public void setChildConditions(final List<ScopedContentCondition> childConditions) {
		this.childConditions = childConditions;
	}

	public List<ScopedContentCondition> getParentConditions() {
		return this.parentConditions;
	}

	public void setParentConditions(final List<ScopedContentCondition> parentConditions) {
		this.parentConditions = parentConditions;
	}
}
