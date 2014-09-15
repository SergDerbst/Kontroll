package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerificationException;
import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

@Component
public class ConditionAttributeBooleanValueVerifier extends ConditionAttributeValueVerifier<Boolean> {

	@Override
	protected boolean isResponsible(final Class<?> valueType) {
		return Boolean.class.equals(valueType) || Boolean.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Boolean> getComparator() {
		throw new ConditionAttributeValueVerificationException("Cannot compare booleans.");
	}

	@Override
	protected boolean isEqual(final Boolean expectedValue, final Boolean actualValue) {
		if (!super.checkNullEquality(expectedValue, actualValue)) {
			return expectedValue != null && actualValue != null && expectedValue.booleanValue() == actualValue.booleanValue();
		}
		return true;
	}
}
