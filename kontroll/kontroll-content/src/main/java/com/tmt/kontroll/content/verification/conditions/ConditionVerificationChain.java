package com.tmt.kontroll.content.verification.conditions;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentDto;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.verification.conditions.impl.AndConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.AttributesConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.NotConditionVerifier;
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
	NotConditionVerifier notVerifier;
	@Autowired
	XorConditionVerifier xorVerifier;
	
	@PostConstruct
	public void setUpVerificationChain() {
		this.attributeVerifier.setNextVerifier(andVerifier);
		this.andVerifier.setNextVerifier(orVerifier);
		this.orVerifier.setNextVerifier(notVerifier);
		this.notVerifier.setNextVerifier(xorVerifier);
		this.xorVerifier.setNextVerifier(null);
	}
	
	public boolean verify(final ScopedContentCondition condition, final ContentDto contentDto) {
		return this.attributeVerifier.verify(condition, contentDto);
	}
}
