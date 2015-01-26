package com.tmt.kontroll.content.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.IndexColumn;

import com.tmt.kontroll.persistence.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
public class ScopedContent extends BaseEntity {

	@Column(nullable = false)
	private String									name;

	@Column(length = DatabaseDefinitions.String_Large)
	private String									description;

	@ManyToOne
	@JoinColumn(name = "scope", nullable = false)
	private Scope										scope;

	@ManyToMany(fetch = FetchType.EAGER)
	@IndexColumn(name = "id")
	@JoinTable(name = "ScopedContent_ScopedContentItem", joinColumns = @JoinColumn(name = "scoped_content_item_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "scoped_content_id", referencedColumnName = "id"))
	private List<ScopedContentItem>	scopedContentItems;

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

	public Scope getScope() {
		return this.scope;
	}

	public void setScope(final Scope scope) {
		this.scope = scope;
	}

	public List<ScopedContentItem> getScopedContentItems() {
		return this.scopedContentItems;
	}

	public void setScopedContentItems(final List<ScopedContentItem> scopedContentItems) {
		this.scopedContentItems = scopedContentItems;
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
		final ScopedContent other = (ScopedContent) o;
		final EqualsBuilder equals = new EqualsBuilder();
		equals.append(this.description, other.description);
		equals.append(this.name, other.name);
		equals.append(this.scope, other.scope);
		equals.append(this.scopedContentItems, other.scopedContentItems);
		return equals.build();
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hashCode = new HashCodeBuilder(17, 37);
		hashCode.append(this.description);
		hashCode.append(this.name);
		hashCode.append(this.scope);
		hashCode.append(this.scopedContentItems);
		return hashCode.build();
	}
}
