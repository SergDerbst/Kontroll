package com.tmt.kontroll.content.verification.conditions.attributes.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.content.verification.conditions.attributes.ConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerificationChain;

@RunWith(MockitoJUnitRunner.class)
public class NotAndConditionAttributeVerifierTest {

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
	private NotAndConditionAttributeVerifier toTest;

	@Before
	public void setUp() throws Exception {
		this.toTest = new NotAndConditionAttributeVerifier();
		this.toTest.verificationChain = this.verificationChain;
		when(this.verificationChain.verify(this.attributeFalse, this.contentDto)).thenReturn(false);
		when(this.verificationChain.verify(this.attributeTrue, this.contentDto)).thenReturn(true);
	}

	@Test
	@SuppressWarnings("serial")
	public void testThatVerifyReturnsTrueWhenOneConditionIsFalse() {
		//given
		this.attributes = new ArrayList<ScopedContentConditionAttribute>() {
			{
				this.add(NotAndConditionAttributeVerifierTest.this.attributeFalse);
				this.add(NotAndConditionAttributeVerifierTest.this.attributeTrue);
			}
		};

		//when
		final boolean verified = this.toTest.verify(this.attributes, ConditionalOperator.NotAnd, this.contentDto);

		//then
		assertTrue(verified);
		verify(this.verificationChain).verify(this.attributeFalse, this.contentDto);
		verify(this.verificationChain).verify(this.attributeTrue, this.contentDto);
	}

	@Test
	@SuppressWarnings("serial")
	public void testThatVerifyReturnsFalseWhenAllConditionsAreTrue() {
		//given
		this.attributes = new ArrayList<ScopedContentConditionAttribute>() {
			{
				this.add(NotAndConditionAttributeVerifierTest.this.attributeTrue);
				this.add(NotAndConditionAttributeVerifierTest.this.attributeTrue);
			}
		};

		//when
		final boolean verified = this.toTest.verify(this.attributes, ConditionalOperator.NotAnd, this.contentDto);

		//then
		assertFalse(verified);
		verify(this.verificationChain, times(2)).verify(this.attributeTrue, this.contentDto);
	}

	@Test
	@SuppressWarnings("serial")
	public void testThatVerifyReturnsTrueWhenAllConditionsAreFalse() {
		//given
		this.attributes = new ArrayList<ScopedContentConditionAttribute>() {
			{
				this.add(NotAndConditionAttributeVerifierTest.this.attributeFalse);
				this.add(NotAndConditionAttributeVerifierTest.this.attributeFalse);
			}
		};

		//when
		final boolean verified = this.toTest.verify(this.attributes, ConditionalOperator.NotAnd, this.contentDto);

		//then
		assertTrue(verified);
		verify(this.verificationChain, times(2)).verify(this.attributeFalse, this.contentDto);
	}

	@Test
	public void testThatNextVerifierIsCalled() {
		//given
		this.toTest.setNextVerifier(this.nextVerifier);

		//when
		this.toTest.verify(this.attributes, ConditionalOperator.Or, this.contentDto);

		//then
		verify(this.nextVerifier).verify(this.attributes, ConditionalOperator.Or, this.contentDto);
	}

	@Test(expected = RuntimeException.class)
	public void testThatExceptionIsThrownForNonExistingNextVerifier() {
		//given
		this.toTest.setNextVerifier(null);

		//when
		this.toTest.verify(this.attributes, ConditionalOperator.Or, this.contentDto);
	}
}
