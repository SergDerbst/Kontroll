package com.tmt.kontroll.content.persistence.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.persistence.entities.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_name", columnNames = {"name"})})
public class Scope extends BaseEntity {

	@Column(nullable = false, length = DatabaseDefinitions.String_Small)
	private String	name;

	@Column(length = DatabaseDefinitions.String_Large)
	private String	description;

	@OneToMany(cascade = CascadeType.DETACH, mappedBy = "scope")
	private List<ScopedContent> scopedContents;

	@ManyToMany
	@JoinTable(name = "Scope_RequestContext",
	joinColumns = @JoinColumn(name = "scope_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "request_context_id", referencedColumnName = "id"))
	private List<String> requestContexts;

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

	public List<ScopedContent> getScopedContents() {
		return this.scopedContents;
	}

	public void setScopedContents(final List<ScopedContent> scopedContents) {
		this.scopedContents = scopedContents;
	}

	public List<String> getRequestContexts() {
		return this.requestContexts;
	}

	public void setRequestContexts(final List<String> requestContexts) {
		this.requestContexts = requestContexts;
	}
}
