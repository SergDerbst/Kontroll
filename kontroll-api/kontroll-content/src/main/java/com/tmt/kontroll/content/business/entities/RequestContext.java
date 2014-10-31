package com.tmt.kontroll.content.business.entities;

import java.sql.Timestamp;
import java.util.List;

import com.tmt.kontroll.business.annotations.BusinessEntity;

@BusinessEntity(com.tmt.kontroll.content.persistence.entities.RequestContext.class)
public class RequestContext {

	private Integer			id;
	private Timestamp		timestamp;
	private String			path;
	private List<Scope>	scopes;

	public String getPath() {
		return this.path;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public List<Scope> getScopes() {
		return this.scopes;
	}

	public void setScopes(final List<Scope> scopes) {
		this.scopes = scopes;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(final Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
