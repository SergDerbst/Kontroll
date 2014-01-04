package com.tmt.kontroll.content.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.content.persistence.selections.ReferenceOperator;
import com.tmt.kontroll.persistence.entities.AbstractBaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "unique_key", columnNames = { "key", "className", "valuePath" }) })
public class ScopedContentConditionAttribute extends AbstractBaseEntity {

	@Column(nullable = false, length = DatabaseDefinitions.String_Small)
	private String						key;

	@Column(nullable = false)
	private int								checkOrder;

	@Column(nullable = false, length = DatabaseDefinitions.String_Large)
	private String						className;

	@Column(nullable = false, length = DatabaseDefinitions.String_Large)
	private String						valuePath;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ReferenceOperator	operator;

	@Column(nullable = false, length = DatabaseDefinitions.String_Large)
	private String						expectedValue;
	
	@Column(nullable = false, length = DatabaseDefinitions.String_Large)
	private String 						expectedValueType;

	@ManyToOne
	@JoinColumn(name = "scopedContentCondition", nullable = false)
	private ScopedContentCondition scopedContentCondition;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getCheckOrder() {
		return checkOrder;
	}

	public void setCheckOrder(int checkOrder) {
		this.checkOrder = checkOrder;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getValuePath() {
		return valuePath;
	}

	public void setValuePath(String valuePath) {
		this.valuePath = valuePath;
	}

	public ReferenceOperator getOperator() {
		return operator;
	}

	public void setOperator(ReferenceOperator operator) {
		this.operator = operator;
	}

	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}

	public ScopedContentCondition getScopedContentCondition() {
		return scopedContentCondition;
	}

	public void setScopedContentCondition(ScopedContentCondition scopedContentCondition) {
		this.scopedContentCondition = scopedContentCondition;
	}

	public String getExpectedValueType() {
		return expectedValueType;
	}

	public void setExpectedValueType(String expectedValueType) {
		this.expectedValueType = expectedValueType;
	}
}
