package com.tmt.kontroll.content.verification.conditions.attributes;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.content.business.content.ContentDto;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.content.verification.conditions.attributes.impl.AndConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.impl.NotAndConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.impl.NotOrConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.impl.NotXorConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.impl.OrConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.impl.XorConditionAttributeVerifier;

@RunWith(MockitoJUnitRunner.class)
public class ConditionAttributeVerificationChainTest {

	@Mock
	private AndConditionAttributeVerifier andConditionAttributeVerifier;
	@Mock
	private OrConditionAttributeVerifier orConditionAttributeVerifier;
	@Mock
	private XorConditionAttributeVerifier xorConditionAttributeVerifier;
	@Mock
	private NotAndConditionAttributeVerifier notAndConditionAttributeVerifier;
	@Mock
	private NotOrConditionAttributeVerifier notOrConditionAttributeVerifier;
	@Mock
	private NotXorConditionAttributeVerifier notXorConditionAttributeVerifier;
	@Mock
	private ContentDto contentDto;

	private ConditionAttributeVerificationChain toTest;

	@Before
	public void setUp() throws Exception {
		this.toTest = new ConditionAttributeVerificationChain();
		this.toTest.andConditionAttributeVerifier = this.andConditionAttributeVerifier;
		this.toTest.orConditionAttributeVerifier = this.orConditionAttributeVerifier;
		this.toTest.xorConditionAttributeVerifier = this.xorConditionAttributeVerifier;
		this.toTest.notAndConditionAttributeVerifier = this.notAndConditionAttributeVerifier;
		this.toTest.notOrConditionAttributeVerifier = this.notOrConditionAttributeVerifier;
		this.toTest.notXorConditionAttributeVerifier = this.notXorConditionAttributeVerifier;
	}

	@Test
	public void testThatSetUpVerificationChainWorks() throws Exception {
		//when
		this.toTest.setUpVerificationChain();

		//then
		verify(this.andConditionAttributeVerifier).setNextVerifier(this.orConditionAttributeVerifier);
		verify(this.orConditionAttributeVerifier).setNextVerifier(this.notAndConditionAttributeVerifier);
		verify(this.notAndConditionAttributeVerifier).setNextVerifier(this.notOrConditionAttributeVerifier);
		verify(this.notOrConditionAttributeVerifier).setNextVerifier(this.xorConditionAttributeVerifier);
		verify(this.xorConditionAttributeVerifier).setNextVerifier(this.notXorConditionAttributeVerifier);
		verify(this.notXorConditionAttributeVerifier).setNextVerifier(null);
	}

	@Test
	public void testThatVerifyStartsChain() throws Exception {
		//given
		final ArrayList<ScopedContentConditionAttribute> attributes = new ArrayList<ScopedContentConditionAttribute>();

		//when
		this.toTest.verify(attributes, ConditionalOperator.And, this.contentDto);

		//then
		verify(this.andConditionAttributeVerifier).verify(attributes, ConditionalOperator.And, this.contentDto);
	}
}
