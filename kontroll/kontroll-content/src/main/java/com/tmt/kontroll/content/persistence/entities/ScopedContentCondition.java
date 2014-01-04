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
import com.tmt.kontroll.persistence.entities.AbstractBaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
public class ScopedContentCondition extends AbstractBaseEntity {

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
	
	@JoinTable(name = "NestedScopedContentConditions",
	joinColumns = @JoinColumn(name = "parent_condition_id", referencedColumnName = "id"),
					inverseJoinColumns = @JoinColumn(name = "child_condition_id", referencedColumnName = "id"),
					uniqueConstraints = { @UniqueConstraint(name = "unique_condition_nesting",
																									columnNames = { "parent_condition_id", "child_condition_id" }) })
	private List<ScopedContentCondition> childConditions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ConditionalOperator getOperator() {
		return operator;
	}

	public void setOperator(ConditionalOperator operator) {
		this.operator = operator;
	}

	public List<ScopedContent> getScopedContents() {
		return scopedContents;
	}

	public void setScopedContents(List<ScopedContent> scopedContents) {
		this.scopedContents = scopedContents;
	}

	public List<ScopedContentItem> getScopedContentItems() {
		return scopedContentItems;
	}

	public void setScopedContentItems(List<ScopedContentItem> scopedContentItems) {
		this.scopedContentItems = scopedContentItems;
	}

	public List<ScopedContentConditionAttribute> getScopedContentConditionAttributes() {
		return scopedContentConditionAttributes;
	}

	public void setScopedContentConditionAttributes(List<ScopedContentConditionAttribute> scopedContentConditionAttributes) {
		this.scopedContentConditionAttributes = scopedContentConditionAttributes;
	}

	public List<ScopedContentCondition> getChildConditions() {
		return childConditions;
	}

	public void setChildConditions(List<ScopedContentCondition> childConditions) {
		this.childConditions = childConditions;
	}
}
