package com.tmt.kontroll.test.dao.entity.values;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvider;

public abstract class ValueFieldProviderTest {

	protected static final String randomFieldName = "blubb";

	@Mock
	private ValueFieldProvider<?> nextProvider;

	protected ValueFieldProvider<?> toTest;
	private final Class<?> nonResponsibleClass;
	private final Object nonResponsibleValue;

	protected ValueFieldProviderTest(final ValueFieldProvider<?> toTest,
	                                 final Class<?> nonResponsibleClass,
	                                 final Object nonResponsibleValue) {
		this.toTest = toTest;
		this.nonResponsibleClass = nonResponsibleClass;
		this.nonResponsibleValue = nonResponsibleValue;
	}

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testThatNextProviderIsCalledFromProvide() {
		//given
		this.toTest.setNextProvider(this.nextProvider);
		final Class<?> valueClass = this.nonResponsibleClass;

		//when
		this.toTest.provide(randomFieldName, valueClass);

		//then
		verify(this.nextProvider).provide(randomFieldName, valueClass);
	}

	@Test
	public void testThatNextProviderIsCalledFromOffset() {
		//given
		this.toTest.setNextProvider(this.nextProvider);

		//when
		this.toTest.offset(randomFieldName, this.nonResponsibleValue);

		//then
		verify(this.nextProvider).offset(randomFieldName, this.nonResponsibleValue);
	}

	@Test
	public void testThatNextProviderIsCalledFromInit() {
		//given
		this.toTest.setNextProvider(this.nextProvider);

		//when
		this.toTest.init(randomFieldName, this.nonResponsibleValue);

		//then
		verify(this.nextProvider).init(randomFieldName, this.nonResponsibleValue);
	}

	@Test
	public void testThatNextProviderIsCalledFromReset() {
		//given
		this.toTest.setNextProvider(this.nextProvider);

		//when
		this.toTest.reset();

		//then
		verify(this.nextProvider).reset();
	}

	@Test
	public void testThatNextProviderIsCalledFromOffsetWithSteps() {
		//given
		this.toTest.setNextProvider(this.nextProvider);

		//when
		this.toTest.increase(10);

		//then
		verify(this.nextProvider).increase(10);
	}

	@Test(expected = RuntimeException.class)
	public void testThatExceptionIsThrownByProvideWhenNextProviderIsNull() {
		//given
		this.toTest.setNextProvider(null);

		//when
		this.toTest.provide(randomFieldName, this.nonResponsibleClass);
	}

	@Test(expected = RuntimeException.class)
	public void testThatExceptionIsThrownByOffsetWhenNextProviderIsNull() {
		//given
		this.toTest.setNextProvider(null);

		//when
		this.toTest.offset(randomFieldName, this.nonResponsibleValue);
	}

	@Test(expected = RuntimeException.class)
	public void testThatExceptionIsThrownByInitWhenNextProviderIsNull() {
		//given
		this.toTest.setNextProvider(null);

		//when
		this.toTest.init(randomFieldName, this.nonResponsibleValue);
	}

	@Test
	public void testThatNoExceptionIsThrownByResetWhenNextProviderIsNull() {
		//given
		this.toTest.setNextProvider(null);

		//when
		this.toTest.reset();
	}

	@Test
	public void testThatNoExceptionIsThrownByIncreaseWithStepsWhenNextProviderIsNull() {
		//given
		this.toTest.setNextProvider(null);

		//when
		this.toTest.increase(10);
	}
}
