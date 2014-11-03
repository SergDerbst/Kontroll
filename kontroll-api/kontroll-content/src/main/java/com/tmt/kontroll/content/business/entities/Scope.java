package com.tmt.kontroll.content.business.entities;

import java.sql.Timestamp;
import java.util.List;

import com.tmt.kontroll.business.annotations.BusinessEntity;

@BusinessEntity(com.tmt.kontroll.content.persistence.entities.Scope.class)
public class Scope {

	private Integer								id;
	private Timestamp							timestamp;
	private String								name;
	private String								description;
	private List<ScopedContent>		scopedContents;
	private List<RequestContext>	requestContexts;

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

	public List<RequestContext> getRequestContexts() {
		return this.requestContexts;
	}

	public void setRequestContexts(final List<RequestContext> requestContexts) {
		this.requestContexts = requestContexts;
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