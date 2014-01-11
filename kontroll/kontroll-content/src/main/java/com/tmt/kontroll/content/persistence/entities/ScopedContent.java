package com.tmt.kontroll.content.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.tmt.kontroll.persistence.entities.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
public class ScopedContent extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(length = DatabaseDefinitions.String_Large)
	private String description;

	@ManyToOne
	@JoinColumn(name = "scope", nullable = false)
	private Scope	scope;

	@ManyToMany
	@JoinTable(name = "ScopedContent_ScopedContentItem",
							joinColumns = @JoinColumn(name = "scoped_content_item_id", referencedColumnName = "id"),
							inverseJoinColumns = @JoinColumn(name = "scoped_content_id", referencedColumnName = "id"))
	private List<ScopedContentItem>	scopedContentItems;
	
	@ManyToMany(mappedBy = "scopedContents")
	private List<ScopedContentCondition>	conditions;

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

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public List<ScopedContentItem> getScopedContentItems() {
		return scopedContentItems;
	}

	public void setScopedContentItems(List<ScopedContentItem> scopedContentItems) {
		this.scopedContentItems = scopedContentItems;
	}

	public List<ScopedContentCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<ScopedContentCondition> conditions) {
		this.conditions = conditions;
	}
}
