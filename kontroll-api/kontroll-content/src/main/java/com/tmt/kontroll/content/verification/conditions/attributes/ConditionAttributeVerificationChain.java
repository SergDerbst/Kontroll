package com.tmt.kontroll.content.verification.conditions.attributes;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.content.ContentDto;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.content.verification.conditions.attributes.impl.AndConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.impl.NotAndConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.impl.NotOrConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.impl.NotXorConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.impl.OrConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.impl.XorConditionAttributeVerifier;

@Component
public class ConditionAttributeVerificationChain {

	@Autowired
	AndConditionAttributeVerifier andConditionAttributeVerifier;
	@Autowired
	OrConditionAttributeVerifier orConditionAttributeVerifier;
	@Autowired
	XorConditionAttributeVerifier xorConditionAttributeVerifier;
	@Autowired
	NotAndConditionAttributeVerifier notAndConditionAttributeVerifier;
	@Autowired
	NotOrConditionAttributeVerifier notOrConditionAttributeVerifier;
	@Autowired
	NotXorConditionAttributeVerifier notXorConditionAttributeVerifier;

	@PostConstruct
	public void setUpVerificationChain() {
		this.andConditionAttributeVerifier.setNextVerifier(this.orConditionAttributeVerifier);
		this.orConditionAttributeVerifier.setNextVerifier(this.notAndConditionAttributeVerifier);
		this.notAndConditionAttributeVerifier.setNextVerifier(this.notOrConditionAttributeVerifier);
		this.notOrConditionAttributeVerifier.setNextVerifier(this.xorConditionAttributeVerifier);
		this.xorConditionAttributeVerifier.setNextVerifier(this.notXorConditionAttributeVerifier);
		this.notXorConditionAttributeVerifier.setNextVerifier(null);
	}

	public boolean verify(final List<ScopedContentConditionAttribute> attributes, final ConditionalOperator operator, final ContentDto contentDto) {
		return this.andConditionAttributeVerifier.verify(attributes, operator, contentDto);
	}
}
