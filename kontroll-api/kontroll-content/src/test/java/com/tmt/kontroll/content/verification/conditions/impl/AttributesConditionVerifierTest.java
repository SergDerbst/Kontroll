package com.tmt.kontroll.content.verification.conditions.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.content.ContentDto;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.content.verification.conditions.ConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.ConditionAttributeVerificationChain;

@RunWith(MockitoJUnitRunner.class)
public class AttributesConditionVerifierTest {
	
	@Mock
	private ConditionAttributeVerificationChain verificationChain;
	@Mock
	private ConditionVerifier nextVerifier;
	@Mock
	private ScopedContentCondition condition;
	@Mock 
	private ContentDto contentDto;
	
	private AttributesConditionVerifier toTest;
	private List<ScopedContentCondition> childConditions;
	private final List<ScopedContentConditionAttribute> attributes = new ArrayList<ScopedContentConditionAttribute>();

	@Before
	public void setUp() throws Exception {
		this.toTest = new AttributesConditionVerifier();
		this.toTest.verificationChain = this.verificationChain;
		when(this.condition.getOperator()).thenReturn(ConditionalOperator.And);
		when(this.condition.getConditionAttributes()).thenReturn(this.attributes);
	}

	@Test
	public void testThatConditionAttributeVeificationChainIsStarted() {
		//given
		this.childConditions = new ArrayList<ScopedContentCondition>();
		when(this.condition.getChildConditions()).thenReturn(this.childConditions);
		
		//when
		this.toTest.verify(this.condition, this.contentDto);
		
		//then
		verify(this.verificationChain).verify(this.attributes, ConditionalOperator.And, this.contentDto);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testThatNextVerifierIsCalledWhenChildConditionsExist() {
		//given
		this.childConditions = new ArrayList<ScopedContentCondition>(){{
			add(condition);
		}};
		when(this.condition.getChildConditions()).thenReturn(this.childConditions);
		this.toTest.setNextVerifier(this.nextVerifier);
		
		//when
		this.toTest.verify(this.condition, this.contentDto);
		
		//then
		verify(this.nextVerifier).verify(this.condition, this.contentDto);
	}
	
	@SuppressWarnings("serial")
	@Test(expected = RuntimeException.class)
	public void testThatExceptionIsThrownForNonExistingNextVerifier() {
		//given
		this.childConditions = new ArrayList<ScopedContentCondition>(){{
			add(condition);
		}};
		when(this.condition.getChildConditions()).thenReturn(this.childConditions);
		this.toTest.setNextVerifier(null);
		
		//when
		this.toTest.verify(this.condition, this.contentDto);
	}
}
