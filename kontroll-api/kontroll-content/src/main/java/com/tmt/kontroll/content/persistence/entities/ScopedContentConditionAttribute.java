package com.tmt.kontroll.content.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmt.kontroll.content.persistence.selections.BooleanOperator;
import com.tmt.kontroll.persistence.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_key", columnNames = {"key", "className", "valuePath"})})
public class ScopedContentConditionAttribute extends BaseEntity {

	@Column(nullable = false, length = DatabaseDefinitions.String_Small)
	private String									key;

	@Column(nullable = false)
	private int											checkOrder;

	@Column(nullable = false, length = DatabaseDefinitions.String_Large)
	private String									className;

	@Column(nullable = false, length = DatabaseDefinitions.String_Large)
	private String									valuePath;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private BooleanOperator					operator;

	@Column(nullable = false, length = DatabaseDefinitions.String_Large)
	private String									expectedValue;

	@Column(nullable = false, length = DatabaseDefinitions.String_Large)
	private String									expectedValueType;

	@ManyToOne
	@JoinColumn(name = "scopedContentCondition", nullable = false)
	private ScopedContentCondition	scopedContentCondition;

	public ScopedContentConditionAttribute() {
		System.out.println();
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public int getCheckOrder() {
		return this.checkOrder;
	}

	public void setCheckOrder(final int checkOrder) {
		this.checkOrder = checkOrder;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(final String className) {
		this.className = className;
	}

	public String getValuePath() {
		return this.valuePath;
	}

	public void setValuePath(final String valuePath) {
		this.valuePath = valuePath;
	}

	public BooleanOperator getOperator() {
		return this.operator;
	}

	public void setOperator(final BooleanOperator operator) {
		this.operator = operator;
	}

	public String getExpectedValue() {
		return this.expectedValue;
	}

	public void setExpectedValue(final String expectedValue) {
		this.expectedValue = expectedValue;
	}

	public ScopedContentCondition getScopedContentCondition() {
		return this.scopedContentCondition;
	}

	public void setScopedContentCondition(final ScopedContentCondition scopedContentCondition) {
		this.scopedContentCondition = scopedContentCondition;
	}

	public String getExpectedValueType() {
		return this.expectedValueType;
	}

	public void setExpectedValueType(final String expectedValueType) {
		this.expectedValueType = expectedValueType;
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
		final ScopedContentConditionAttribute other = (ScopedContentConditionAttribute) o;
		final EqualsBuilder equals = new EqualsBuilder();
		equals.append(this.checkOrder, other.checkOrder);
		equals.append(this.className, this.className);
		equals.append(this.expectedValue, other.expectedValue);
		equals.append(this.expectedValueType, other.expectedValueType);
		equals.append(this.key, other.key);
		equals.append(this.operator, other.operator);
		equals.append(this.scopedContentCondition, this.scopedContentCondition);
		equals.append(this.valuePath, this.valuePath);
		return equals.build();
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hashCode = new HashCodeBuilder(17, 37);
		hashCode.append(this.checkOrder);
		hashCode.append(this.className);
		hashCode.append(this.expectedValue);
		hashCode.append(this.expectedValueType);
		hashCode.append(this.key);
		hashCode.append(this.operator);
		hashCode.append(this.scopedContentCondition);
		hashCode.append(this.valuePath);
		return hashCode.build();
	}
}
