package com.tmt.kontroll.content.verification.conditions.attributes.values;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.content.ContentDto;
import com.tmt.kontroll.content.persistence.conversion.ValueStringConversionChain;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.selections.BooleanOperator;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeBooleanValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeByteValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeCharacterValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeDoubleValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeFloatValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeIntegerValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeLongValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeShortValueVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.impl.ConditionAttributeStringValueVerifier;
import com.tmt.kontroll.context.request.RequestContextItem;
import com.tmt.kontroll.context.request.data.path.processing.BeanPathProcessor;

@RunWith(MockitoJUnitRunner.class)
public class ConditionAttributeValueVerificationChainTest {

	@Mock
	private BeanPathProcessor pathProcessor;
	@Mock
	private ValueStringConversionChain valueConversionChain;
	@Mock
	private ConditionAttributeBooleanValueVerifier booleanConditionAttributeVerifier;
	@Mock
	private ConditionAttributeByteValueVerifier byteConditionAttributeVerifier;
	@Mock
	private ConditionAttributeCharacterValueVerifier characterConditionAttributeVerifier;
	@Mock
	private ConditionAttributeDoubleValueVerifier doubleConditionAttributeVerifier;
	@Mock
	private ConditionAttributeFloatValueVerifier floatConditionAttributeVerifier;
	@Mock
	private ConditionAttributeIntegerValueVerifier integerConditionAttributeVerifier;
	@Mock
	private ConditionAttributeLongValueVerifier longConditionAttributeVerifier;
	@Mock
	private ConditionAttributeShortValueVerifier shortConditionAttributeVerifier;
	@Mock
	private ConditionAttributeStringValueVerifier stringConditionAttributeVerifier;
	@Mock
	private ScopedContentConditionAttribute attribute;
	@Mock
	private ContentDto contentDto;
	@Mock
	private Set<RequestContextItem> requestContext;

	@InjectMocks
	private ConditionAttributeValueVerificationChain toTest;

	private final Integer expectedValue = 0;
	private final Integer actualValue = 0;

	@Before
	public void setUp() throws Exception {
		when(this.attribute.getExpectedValueType()).thenReturn(this.getClass().getName());
		when(this.attribute.getExpectedValue()).thenReturn("bla");
		when(this.attribute.getOperator()).thenReturn(BooleanOperator.IsEqual);
		when(this.contentDto.getRequestContext()).thenReturn(this.requestContext);
		when(this.valueConversionChain.convertToValue(any(String.class), any(Class.class))).thenReturn(this.expectedValue);
		when(this.pathProcessor.process(any(String.class), any(Set.class))).thenReturn(this.actualValue);
	}

	@Test
	public void testThatSetUpVerificationChainWorks() throws Exception {
		//when
		this.toTest.setUpVerificationChain();

		//then
		verify(this.stringConditionAttributeVerifier).setNextVerifier(this.integerConditionAttributeVerifier);
		verify(this.integerConditionAttributeVerifier).setNextVerifier(this.booleanConditionAttributeVerifier);
		verify(this.booleanConditionAttributeVerifier).setNextVerifier(this.longConditionAttributeVerifier);
		verify(this.longConditionAttributeVerifier).setNextVerifier(this.doubleConditionAttributeVerifier);
		verify(this.doubleConditionAttributeVerifier).setNextVerifier(this.floatConditionAttributeVerifier);
		verify(this.floatConditionAttributeVerifier).setNextVerifier(this.shortConditionAttributeVerifier);
		verify(this.shortConditionAttributeVerifier).setNextVerifier(this.byteConditionAttributeVerifier);
		verify(this.byteConditionAttributeVerifier).setNextVerifier(this.characterConditionAttributeVerifier);
		verify(this.characterConditionAttributeVerifier).setNextVerifier(null);
	}

	@Test
	public void testThatVerifyStartsVerificationChain() throws Exception {
		//when
		this.toTest.verify(this.attribute, this.contentDto);

		//then
		verify(this.stringConditionAttributeVerifier).verify(this.expectedValue, this.actualValue, this.getClass(), BooleanOperator.IsEqual);
	}

	@Test(expected = RuntimeException.class)
	public void testThatVerifyThrowsRuntimeException() throws Exception {
		//given
		when(this.attribute.getExpectedValueType()).thenThrow(new RuntimeException());

		//when
		this.toTest.verify(this.attribute, this.contentDto);
	}

}
