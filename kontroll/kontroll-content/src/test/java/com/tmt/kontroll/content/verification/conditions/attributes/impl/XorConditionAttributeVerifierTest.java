package com.tmt.kontroll.content.verification.conditions.attributes.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.content.ContentDto;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.content.verification.conditions.attributes.ConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerificationChain;

public class XorConditionAttributeVerifierTest {

	@Mock
	private ConditionAttributeValueVerificationChain verificationChain;
	@Mock
	private ConditionAttributeVerifier nextVerifier;
	@Mock
	private ScopedContentConditionAttribute attributeFalse;
	@Mock
	private ScopedContentConditionAttribute attributeTrue;
	@Mock
	private ContentDto contentDto;

	private List<ScopedContentConditionAttribute> attributes;

	private XorConditionAttributeVerifier toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.toTest = new XorConditionAttributeVerifier();
		this.toTest.verificationChain = this.verificationChain;
		when(this.verificationChain.verify(this.attributeFalse, this.contentDto)).thenReturn(false);
		when(this.verificationChain.verify(this.attributeTrue, this.contentDto)).thenReturn(true);
	}

	@Test
	@SuppressWarnings("serial")
	public void testThatVerifyReturnsFalse() {
		//given
		this.attributes = new ArrayList<ScopedContentConditionAttribute>() {
			{
				add(attributeFalse);
				add(attributeFalse);
			}
		};
		
		//when
		final boolean verified = this.toTest.verify(attributes, ConditionalOperator.Xor, this.contentDto);

		//then
		assertFalse(verified);
		verify(this.verificationChain, times(2)).verify(this.attributeFalse, this.contentDto);
		verify(this.verificationChain, never()).verify(this.attributeTrue, this.contentDto);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testThatVerifyReturnsTrueIfOneIsTrue() {
		//given
		this.attributes = new ArrayList<ScopedContentConditionAttribute>() {
			{
				add(attributeFalse);
				add(attributeTrue);
				add(attributeFalse);
			}
		};
		
		//when
		final boolean verified = this.toTest.verify(attributes, ConditionalOperator.Xor, this.contentDto);
		
		//then
		assertTrue(verified);
		verify(this.verificationChain).verify(this.attributeTrue, this.contentDto);
		verify(this.verificationChain, times(2)).verify(this.attributeFalse, this.contentDto);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testThatVerifyReturnsFalseIfAllAreTrue() {
		//given
		this.attributes = new ArrayList<ScopedContentConditionAttribute>() {
			{
				add(attributeTrue);
				add(attributeTrue);
			}
		};
		
		//when
		final boolean verified = this.toTest.verify(attributes, ConditionalOperator.Xor, this.contentDto);
		
		//then
		assertFalse(verified);
		verify(this.verificationChain, times(2)).verify(this.attributeTrue, this.contentDto);
		verify(this.verificationChain, never()).verify(this.attributeFalse, this.contentDto);
	}

	@Test
	public void testThatNextVerifierIsCalled() {
		//given
		this.toTest.setNextVerifier(this.nextVerifier);

		//when
		this.toTest.verify(attributes, ConditionalOperator.And, this.contentDto);

		//then
		verify(this.nextVerifier).verify(this.attributes, ConditionalOperator.And, this.contentDto);
	}
	
	@Test(expected = RuntimeException.class)
	public void testThatExceptionIsThrownForNonExistingNextVerifier() {
		//given
		this.toTest.setNextVerifier(null);

		//when
		this.toTest.verify(attributes, ConditionalOperator.And, this.contentDto);
	}
}
