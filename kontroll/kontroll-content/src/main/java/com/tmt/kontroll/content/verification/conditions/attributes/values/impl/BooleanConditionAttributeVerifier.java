package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerificationException;
import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

@Component
public class BooleanConditionAttributeVerifier extends ConditionAttributeValueVerifier<Boolean> {

	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Boolean.class.equals(valueType) || Boolean.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Boolean> getComparator() {
		throw new ConditionAttributeValueVerificationException("Cannot compare booleans.");
	}

	@Override
	protected boolean isEqual(Boolean expectedValue, Boolean actualValue) {
		return super.checkNullEquality(expectedValue, actualValue) || expectedValue.booleanValue() == actualValue.booleanValue();
	}
}
