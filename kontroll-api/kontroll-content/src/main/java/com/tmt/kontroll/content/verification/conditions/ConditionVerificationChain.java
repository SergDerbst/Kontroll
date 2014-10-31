package com.tmt.kontroll.content.verification.conditions;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.content.ContentDto;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.verification.conditions.impl.AndConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.AttributesConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.NotAndConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.NotOrConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.NotXorConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.OrConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.XorConditionVerifier;

@Component
public class ConditionVerificationChain {

	@Autowired
	AttributesConditionVerifier attributeVerifier;
	@Autowired
	AndConditionVerifier andVerifier;
	@Autowired
	OrConditionVerifier orVerifier;
	@Autowired
	XorConditionVerifier xorVerifier;
	@Autowired
	NotAndConditionVerifier notAndVerifier;
	@Autowired
	NotOrConditionVerifier notOrVerifier;
	@Autowired
	NotXorConditionVerifier notXorVerifier;
	
	@PostConstruct
	public void setUpVerificationChain() {
		this.attributeVerifier.setNextVerifier(this.andVerifier);
		this.andVerifier.setNextVerifier(this.orVerifier);
		this.orVerifier.setNextVerifier(this.notAndVerifier);
		this.notAndVerifier.setNextVerifier(this.notOrVerifier);
		this.notOrVerifier.setNextVerifier(this.xorVerifier);
		this.xorVerifier.setNextVerifier(this.notXorVerifier);
		this.notXorVerifier.setNextVerifier(null);
	}
	
	public boolean verify(final ScopedContentCondition condition, final ContentDto contentDto) {
		return this.attributeVerifier.verify(condition, contentDto);
	}
}
