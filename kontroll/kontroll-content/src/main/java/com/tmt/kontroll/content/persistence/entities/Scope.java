package com.tmt.kontroll.content.persistence.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.persistence.entities.AbstractBaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_name", columnNames = {"name"})})
public class Scope extends AbstractBaseEntity {

	@Column(nullable = false, length = DatabaseDefinitions.String_Small)
	private String	name;

	@Column(length = DatabaseDefinitions.String_Large)
	private String	description;
	
	@OneToMany(cascade = CascadeType.DETACH, mappedBy = "scope")
	private List<ScopedContent> scopedContents;

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

	public List<ScopedContent> getScopedContents() {
		return scopedContents;
	}

	public void setScopedContents(List<ScopedContent> scopedContents) {
		this.scopedContents = scopedContents;
	}
}
