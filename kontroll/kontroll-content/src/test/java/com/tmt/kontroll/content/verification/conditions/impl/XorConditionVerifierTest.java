package com.tmt.kontroll.content.verification.conditions.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.content.verification.conditions.ConditionVerificationChain;
import com.tmt.kontroll.content.verification.conditions.ConditionVerifier;

@RunWith(MockitoJUnitRunner.class)
public class XorConditionVerifierTest {

	@Mock
	private ConditionVerificationChain verificationChain;
	@Mock
	private ConditionVerifier nextVerifier;
	@Mock
	private ScopedContentCondition condition;
	@Mock
	private ScopedContentCondition childConditionTrue;
	@Mock
	private ScopedContentCondition childConditionFalse;
	@Mock
	private ContentDto contentDto;
	
	private XorConditionVerifier toTest;
	private List<ScopedContentCondition> childConditions;

	@Before
	public void setUp() throws Exception {
		this.toTest = new XorConditionVerifier();
		this.toTest.verificationChain = this.verificationChain;
		when(this.verificationChain.verify(this.childConditionFalse, this.contentDto)).thenReturn(false);
		when(this.verificationChain.verify(this.childConditionTrue, this.contentDto)).thenReturn(true);
	}

	@Test
	@SuppressWarnings("serial")
	public void testThatVerifyReturnsFalseWhenAllConditionsAreTrue() {
		//given
		this.childConditions = new ArrayList<ScopedContentCondition>(){{
			add(childConditionTrue);
			add(childConditionTrue);
		}};
		when(this.condition.getChildConditions()).thenReturn(this.childConditions);
		when(this.condition.getOperator()).thenReturn(ConditionalOperator.Xor);
		
		//when
		final boolean verified = this.toTest.verify(this.condition, this.contentDto);
		
		//then
		assertFalse(verified);
		verify(this.verificationChain, times(2)).verify(this.childConditionTrue, this.contentDto);
		verify(this.verificationChain, never()).verify(this.childConditionFalse, this.contentDto);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testThatVerifyReturnsTrueWhenOneConditionIsTrue() {
		//given
		this.childConditions = new ArrayList<ScopedContentCondition>(){{
			add(childConditionFalse);
			add(childConditionTrue);
		}};
		when(this.condition.getChildConditions()).thenReturn(this.childConditions);
		when(this.condition.getOperator()).thenReturn(ConditionalOperator.Xor);
		
		//when
		final boolean verified = this.toTest.verify(this.condition, this.contentDto);
		
		//then
		assertTrue(verified);
		verify(this.verificationChain).verify(this.childConditionTrue, this.contentDto);
		verify(this.verificationChain).verify(this.childConditionFalse, this.contentDto);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testThatVerifyReturnsFalseWhenAllConditionsAreFalse() {
		//given
		this.childConditions = new ArrayList<ScopedContentCondition>(){{
			add(childConditionFalse);
			add(childConditionFalse);
		}};
		when(this.condition.getChildConditions()).thenReturn(this.childConditions);
		when(this.condition.getOperator()).thenReturn(ConditionalOperator.Xor);
		
		//when
		final boolean verified = this.toTest.verify(this.condition, this.contentDto);
		
		//then
		assertFalse(verified);
		verify(this.verificationChain, never()).verify(this.childConditionTrue, this.contentDto);
		verify(this.verificationChain, times(2)).verify(this.childConditionFalse, this.contentDto);
	}
	
	@Test
	public void testThatNextVerifierIsCalledWhenChildConditionsAreEmpty() {
		//given
		this.childConditions = new ArrayList<ScopedContentCondition>();
		this.toTest.setNextVerifier(this.nextVerifier);
		when(this.condition.getOperator()).thenReturn(ConditionalOperator.Xor);
		
		//when
		this.toTest.verify(this.condition, this.contentDto);
		
		//then
		verify(this.nextVerifier).verify(this.condition, this.contentDto);
		verify(this.verificationChain, never()).verify(this.childConditionTrue, this.contentDto);
		verify(this.verificationChain, never()).verify(this.childConditionFalse, this.contentDto);
		
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testThatNextVerifierIsCalledWhenOperatorIsOtherThanOr() {
		//given
		this.childConditions = new ArrayList<ScopedContentCondition>(){{
			add(childConditionTrue);
			add(childConditionFalse);
		}};
		this.toTest.setNextVerifier(this.nextVerifier);
		when(this.condition.getOperator()).thenReturn(ConditionalOperator.And);
		
		//when
		this.toTest.verify(this.condition, this.contentDto);
		
		//then
		verify(this.nextVerifier).verify(this.condition, this.contentDto);
		verify(this.verificationChain, never()).verify(this.childConditionTrue, this.contentDto);
		verify(this.verificationChain, never()).verify(this.childConditionFalse, this.contentDto);
		
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testThatNextVerifierIsCalledWhenOperatorIsNull() {
		//given
		this.childConditions = new ArrayList<ScopedContentCondition>(){{
			add(childConditionTrue);
			add(childConditionFalse);
		}};
		this.toTest.setNextVerifier(this.nextVerifier);
		when(this.condition.getOperator()).thenReturn(null);
		
		//when
		this.toTest.verify(this.condition, this.contentDto);
		
		//then
		verify(this.nextVerifier).verify(this.condition, this.contentDto);
		verify(this.verificationChain, never()).verify(this.childConditionTrue, this.contentDto);
		verify(this.verificationChain, never()).verify(this.childConditionFalse, this.contentDto);
		
	}
	
	@SuppressWarnings("serial")
	@Test(expected = RuntimeException.class)
	public void testThatExceptionIsThrownForNonExistingNextVerifier() {
		//given
		this.childConditions = new ArrayList<ScopedContentCondition>(){{
			add(childConditionTrue);
			add(childConditionFalse);
		}};
		this.toTest.setNextVerifier(null);
		when(this.condition.getOperator()).thenReturn(ConditionalOperator.And);
		
		//when
		this.toTest.verify(this.condition, this.contentDto);
	}
}
