package com.tmt.kontroll.content.business.entities;

import java.sql.Timestamp;
import java.util.List;

import com.tmt.kontroll.business.annotations.BusinessEntity;
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;

@BusinessEntity(com.tmt.kontroll.content.persistence.entities.ScopedContentCondition.class)
public class ScopedContentCondition {

	private Integer																id;
	private Timestamp															timestamp;
	private String																name;
	private String																description;
	private ConditionalOperator										operator;
	private List<ScopedContent>										scopedContents;
	private List<ScopedContentItem>								scopedContentItems;
	private List<ScopedContentConditionAttribute>	scopedContentConditionAttributes;
	private List<ScopedContentCondition>					parentConditions;
	private List<ScopedContentCondition>					childConditions;

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

	public ConditionalOperator getOperator() {
		return this.operator;
	}

	public void setOperator(final ConditionalOperator operator) {
		this.operator = operator;
	}

	public List<ScopedContent> getScopedContents() {
		return this.scopedContents;
	}

	public void setScopedContents(final List<ScopedContent> scopedContents) {
		this.scopedContents = scopedContents;
	}

	public List<ScopedContentItem> getScopedContentItems() {
		return this.scopedContentItems;
	}

	public void setScopedContentItems(final List<ScopedContentItem> scopedContentItems) {
		this.scopedContentItems = scopedContentItems;
	}

	public List<ScopedContentConditionAttribute> getConditionAttributes() {
		return this.scopedContentConditionAttributes;
	}

	public void setScopedContentConditionAttributes(final List<ScopedContentConditionAttribute> scopedContentConditionAttributes) {
		this.scopedContentConditionAttributes = scopedContentConditionAttributes;
	}

	public List<ScopedContentConditionAttribute> getScopedContentConditionAttributes() {
		return this.scopedContentConditionAttributes;
	}

	public List<ScopedContentCondition> getChildConditions() {
		return this.childConditions;
	}

	public void setChildConditions(final List<ScopedContentCondition> childConditions) {
		this.childConditions = childConditions;
	}

	public List<ScopedContentCondition> getParentConditions() {
		return this.parentConditions;
	}

	public void setParentConditions(final List<ScopedContentCondition> parentConditions) {
		this.parentConditions = parentConditions;
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
