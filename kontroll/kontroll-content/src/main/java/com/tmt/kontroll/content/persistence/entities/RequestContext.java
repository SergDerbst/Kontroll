package com.tmt.kontroll.content.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.persistence.entities.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_path", columnNames = {"path"})})
public class RequestContext extends BaseEntity {

	@Column(nullable = false, length = DatabaseDefinitions.String_Large)
	private String path;
	
	@ManyToMany(mappedBy = "requestContexts")
	private List<Scope> scopes;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Scope> getScopes() {
		return scopes;
	}

	public void setScopes(List<Scope> scopes) {
		this.scopes = scopes;
	}
}
