package com.tmt.kontroll.content.business.entities;

import java.sql.Timestamp;
import java.util.List;

import com.tmt.kontroll.business.annotations.BusinessEntity;

@BusinessEntity(com.tmt.kontroll.content.persistence.entities.ScopedContent.class)
public class ScopedContent {

	private Integer												id;
	private Timestamp											timestamp;
	private String												name;
	private String												description;
	private Scope													scope;
	private List<ScopedContentItem>				scopedContentItems;
	private List<ScopedContentCondition>	conditions;

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

	public List<ScopedContentCondition> getConditions() {
		return this.conditions;
	}

	public void setConditions(final List<ScopedContentCondition> conditions) {
		this.conditions = conditions;
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
