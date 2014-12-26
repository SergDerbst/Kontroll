package com.tmt.kontroll.content.persistence.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.persistence.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_request_pattern_name", columnNames = {"name", "requestPattern"})})
public class Scope extends BaseEntity {

	@Column(nullable = false)
	private String							name;

	@Column(length = DatabaseDefinitions.String_Large)
	private String							description;

	@OneToMany(cascade = CascadeType.DETACH, mappedBy = "scope")
	private List<ScopedContent>	scopedContents;

	@Column(nullable = false)
	private String							requestPattern;

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

	public String getRequestPattern() {
		return this.requestPattern;
	}

	public void setRequestPattern(final String requestPattern) {
		this.requestPattern = requestPattern;
	}
}
