package com.tmt.kontroll.content.verification.conditions;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.content.business.content.data.ContentOperatingContext;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.verification.conditions.impl.AndConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.AttributesConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.NotAndConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.NotOrConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.NotXorConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.OrConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.impl.XorConditionVerifier;

@RunWith(MockitoJUnitRunner.class)
public class ConditionVerificationChainTest {
	
	@Mock
	private AndConditionVerifier andVerifier;
	@Mock
	private AttributesConditionVerifier attributeVerifier;
	@Mock
	private OrConditionVerifier orVerifier;
	@Mock
	private XorConditionVerifier xorVerifier;
	@Mock
	private NotAndConditionVerifier notAndVerifier;
	@Mock
	private NotOrConditionVerifier notOrVerifier;
	@Mock
	private NotXorConditionVerifier notXorVerifier;
	@Mock
	private ScopedContentCondition condition;
	@Mock
	private ContentOperatingContext contentDto;

	private ConditionVerificationChain toTest;

	@Before
	public void setUp() throws Exception {
		this.toTest = new ConditionVerificationChain();
		this.toTest.andVerifier = this.andVerifier;
		this.toTest.attributeVerifier = this.attributeVerifier;
		this.toTest.orVerifier = this.orVerifier;
		this.toTest.xorVerifier = this.xorVerifier;
		this.toTest.notAndVerifier = this.notAndVerifier;
		this.toTest.notOrVerifier = this.notOrVerifier;
		this.toTest.notXorVerifier = this.notXorVerifier;
	}

	@Test
	public void testSetUpVerificationChain() throws Exception {
		//when
		this.toTest.setUpVerificationChain();
		
		//then
		verify(this.attributeVerifier).setNextVerifier(this.andVerifier);
		verify(this.andVerifier).setNextVerifier(this.orVerifier);
		verify(this.orVerifier).setNextVerifier(this.notAndVerifier);
		verify(this.notAndVerifier).setNextVerifier(this.notOrVerifier);
		verify(this.notOrVerifier).setNextVerifier(this.xorVerifier);
		verify(this.xorVerifier).setNextVerifier(this.notXorVerifier);
		verify(this.notXorVerifier).setNextVerifier(null);
	}

	@Test
	public void testVerify() throws Exception {
		//when
		this.toTest.verify(this.condition, this.contentDto);
		
		//then
		verify(this.attributeVerifier).verify(this.condition, this.contentDto);
	}

}
