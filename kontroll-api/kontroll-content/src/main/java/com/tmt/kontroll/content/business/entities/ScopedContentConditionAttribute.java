package com.tmt.kontroll.content.business.entities;

import java.sql.Timestamp;

import com.tmt.kontroll.business.annotations.BusinessEntity;
import com.tmt.kontroll.content.persistence.selections.BooleanOperator;

@BusinessEntity(com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute.class)
public class ScopedContentConditionAttribute {

	private Integer									id;
	private Timestamp								timestamp;
	private String									key;
	private int											checkOrder;
	private String									className;
	private String									valuePath;
	private BooleanOperator					operator;
	private String									expectedValue;
	private String									expectedValueType;
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
