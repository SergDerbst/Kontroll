package com.tmt.kontroll.content.verification.conditions.attributes.values;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentDto;
import com.tmt.kontroll.content.persistence.conversion.ValueStringConversionChain;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.BooleanConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ByteConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.CharacterConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.DoubleConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.FloatConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.IntegerConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.LongConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ShortConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.StringConditionAttributeVerifier;
import com.tmt.kontroll.context.request.data.path.processing.BeanPathProcessor;

@Component
public class ConditionAttributeValueVerificationChain {

	@Autowired
	BeanPathProcessor pathProcessor;
	@Autowired
	ValueStringConversionChain valueConversionChain;
	
	@Autowired
	BooleanConditionAttributeVerifier booleanConditionAttributeVerifier;
	@Autowired
	ByteConditionAttributeVerifier byteConditionAttributeVerifier;
	@Autowired
	CharacterConditionAttributeVerifier characterConditionAttributeVerifier;
	@Autowired
	DoubleConditionAttributeVerifier doubleConditionAttributeVerifier;
	@Autowired
	FloatConditionAttributeVerifier floatConditionAttributeVerifier;
	@Autowired
	IntegerConditionAttributeVerifier integerConditionAttributeVerifier;
	@Autowired
	LongConditionAttributeVerifier longConditionAttributeVerifier;
	@Autowired
	ShortConditionAttributeVerifier shortConditionAttributeVerifier;
	@Autowired
	StringConditionAttributeVerifier stringConditionAttributeVerifier;
	
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
	                      final ContentDto contentDto) {
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
