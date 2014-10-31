package com.tmt.kontroll.content.verification;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.content.business.content.ContentDto;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.verification.conditions.ConditionInconsistentException;
import com.tmt.kontroll.content.verification.conditions.ConditionVerificationChain;

@RunWith(MockitoJUnitRunner.class)
public class ContentConditionVerifierTest {

	@Mock
	private ConditionVerificationChain verificationChain;
	@Mock
	private ScopedContentCondition condition;
	@Mock
	private ContentDto contentDto;
	@Mock
	private List<ScopedContentCondition> conditionList;
	@Mock
	private List<ScopedContentConditionAttribute> attributeList;

	private ContentConditionVerifier toTest;

	@Before
	public void setUp() throws Exception {
		this.toTest = new ContentConditionVerifier();
		this.toTest.verificationChain = this.verificationChain;
		when(this.condition.getChildConditions()).thenReturn(this.conditionList);
		when(this.condition.getConditionAttributes()).thenReturn(this.attributeList);
	}

	@Test
	public void testThatVerificationChainIsCalledForConditionWithChildConditionsButNoAttributes() {
		//given
		when(this.conditionList.isEmpty()).thenReturn(true);
		when(this.attributeList.isEmpty()).thenReturn(false);

		//when
		this.toTest.verify(this.condition, this.contentDto);

		//then
		verify(this.verificationChain).verify(this.condition, this.contentDto);
	}

	@Test
	public void testThatVerificationChainIsCalledForConditionWithNoChildConditionsButWithAttributes() {
		//given
		when(this.conditionList.isEmpty()).thenReturn(false);
		when(this.attributeList.isEmpty()).thenReturn(true);

		//when
		this.toTest.verify(this.condition, this.contentDto);

		//then
		verify(this.verificationChain).verify(this.condition, this.contentDto);
	}

	@Test(expected = ConditionInconsistentException.class)
	public void testThatVerificationChainIsNotCalledWhenConditionHasNeitherChildConditionsNorAttributes() {
		//given
		when(this.conditionList.isEmpty()).thenReturn(true);
		when(this.attributeList.isEmpty()).thenReturn(true);

		//when
		this.toTest.verify(this.condition, this.contentDto);
	}

	@Test(expected = ConditionInconsistentException.class)
	public void testThatVerificationChainIsNotCalledWhenConditionHasBothChildConditionsAndAttributes() {
		//given
		when(this.conditionList.isEmpty()).thenReturn(false);
		when(this.attributeList.isEmpty()).thenReturn(false);

		//when
		this.toTest.verify(this.condition, this.contentDto);
	}
}
