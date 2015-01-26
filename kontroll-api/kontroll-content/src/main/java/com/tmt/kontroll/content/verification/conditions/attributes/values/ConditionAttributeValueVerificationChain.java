package com.tmt.kontroll.content.verification.conditions.attributes.values;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.content.data.ContentLoadingContext;
import com.tmt.kontroll.content.persistence.conversion.ValueStringConversionChain;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeBooleanValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeByteValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeCharacterValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeDoubleValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeFloatValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeIntegerValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeLongValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeShortValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeStringValueVerifier;
import com.tmt.kontroll.context.request.data.path.processing.BeanPathProcessor;

@Component
public class ConditionAttributeValueVerificationChain {

	@Autowired
	BeanPathProcessor pathProcessor;
	@Autowired
	ValueStringConversionChain valueConversionChain;
	
	@Autowired
	ConditionAttributeBooleanValueVerifier booleanConditionAttributeVerifier;
	@Autowired
	ConditionAttributeByteValueVerifier byteConditionAttributeVerifier;
	@Autowired
	ConditionAttributeCharacterValueVerifier characterConditionAttributeVerifier;
	@Autowired
	ConditionAttributeDoubleValueVerifier doubleConditionAttributeVerifier;
	@Autowired
	ConditionAttributeFloatValueVerifier floatConditionAttributeVerifier;
	@Autowired
	ConditionAttributeIntegerValueVerifier integerConditionAttributeVerifier;
	@Autowired
	ConditionAttributeLongValueVerifier longConditionAttributeVerifier;
	@Autowired
	ConditionAttributeShortValueVerifier shortConditionAttributeVerifier;
	@Autowired
	ConditionAttributeStringValueVerifier stringConditionAttributeVerifier;
	
	@PostConstruct
	public void setUpVerificationChain() {
		this.stringConditionAttributeVerifier.setNextVerifier(this.integerConditionAttributeVerifier);
		this.integerConditionAttributeVerifier.setNextVerifier(this.booleanConditionAttributeVerifier);
		this.booleanConditionAttributeVerifier.setNextVerifier(this.longConditionAttributeVerifier);
		this.longConditionAttributeVerifier.setNextVerifier(this.doubleConditionAttributeVerifier);
		this.doubleConditionAttributeVerifier.setNextVerifier(this.floatConditionAttributeVerifier);
		this.floatConditionAttributeVerifier.setNextVerifier(this.shortConditionAttributeVerifier);
		this.shortConditionAttributeVerifier.setNextVerifier(this.byteConditionAttributeVerifier);
		this.byteConditionAttributeVerifier.setNextVerifier(this.characterConditionAttributeVerifier);
		this.characterConditionAttributeVerifier.setNextVerifier(null);
	}
	
	public boolean verify(final ScopedContentConditionAttribute attribute,
	                      final ContentLoadingContext contentDto) {
		try {
			final Class<?> valueType = Class.forName(attribute.getExpectedValueType());
			final Object expectedValue = this.valueConversionChain.convertToValue(attribute.getExpectedValue(), valueType);
			final Object actualValue = this.pathProcessor.process(attribute.getValuePath(), contentDto.getRequestContext());
			return this.stringConditionAttributeVerifier.verify(expectedValue, actualValue, valueType, attribute.getOperator());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
