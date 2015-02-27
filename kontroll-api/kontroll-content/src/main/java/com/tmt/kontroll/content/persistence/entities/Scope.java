package com.tmt.kontroll.content.persistence.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmt.kontroll.persistence.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_request_pattern_name", columnNames = {"name", "requestPattern"})})
public class Scope extends BaseEntity {

	@Column(nullable = false)
	private String									name;

	@Column(length = DatabaseDefinitions.String_Large)
	private String									description;

	@ManyToMany
	@JoinTable(name = "Scope_ScopeContentItems", joinColumns = @JoinColumn(name = "scope_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "scope_content_item_id", referencedColumnName = "id"))
	private Set<ScopedContentItem>	scopedContentItems;

	@Column(nullable = false)
	private String									requestPattern;

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

	public Set<ScopedContentItem> getScopedContentItems() {
		return this.scopedContentItems;
	}

	public void setScopedContentItems(final Set<ScopedContentItem> scopedContentItems) {
		this.scopedContentItems = scopedContentItems;
	}

	public String getRequestPattern() {
		return this.requestPattern;
	}

	public void setRequestPattern(final String requestPattern) {
		this.requestPattern = requestPattern;
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
		final Scope other = (Scope) o;
		final EqualsBuilder equals = new EqualsBuilder();
		equals.append(this.description, other.description);
		equals.append(this.name, other.name);
		equals.append(this.requestPattern, other.requestPattern);
		equals.append(this.scopedContentItems, other.scopedContentItems);
		return equals.build();
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hashCode = new HashCodeBuilder(17, 37);
		hashCode.append(this.description);
		hashCode.append(this.name);
		hashCode.append(this.requestPattern);
		hashCode.append(this.scopedContentItems);
		return hashCode.build();
	}
}
