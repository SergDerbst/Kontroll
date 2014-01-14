package com.tmt.kontroll.test.dao.entity.values;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.test.dao.entity.values.impl.BooleanFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.ByteFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.CharacterFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.DoubleFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.FloatFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.IntegerFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.LocaleFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.LongFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.ShortFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.StringFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.TimestampFieldValueProvider;

@RunWith(MockitoJUnitRunner.class)
public class ValueFieldProvisionChainTest {

	private static final String randomStringValue = "blubb";
	@Mock
	private BooleanFieldValueProvider booleanFieldValueProvider;
	@Mock
	private ByteFieldValueProvider byteFieldValueProvider;
	@Mock
	private CharacterFieldValueProvider characterFieldValueProvider;
	@Mock
	private DoubleFieldValueProvider doubleFieldValueProvider;
	@Mock
	private FloatFieldValueProvider floatFieldValueProvider;
	@Mock
	private IntegerFieldValueProvider integerFieldValueProvider;
	@Mock
	private LongFieldValueProvider longFieldValueProvider;
	@Mock
	private ShortFieldValueProvider shortFieldValueProvider;
	@Mock
	private StringFieldValueProvider stringFieldValueProvider;
	@Mock
	private TimestampFieldValueProvider timestampFieldValueProvider;
	@Mock
	private LocaleFieldValueProvider localeFieldValueProvider;

	@Mock
	private ValueFieldProvider<?> addedFieldValueProvider;

	@InjectMocks
	private ValueFieldProvisionChain toTest;

	@Before
	public void setUp() throws Exception {}

	@Test
	public void testThatSetUpValueProvisionChainWorks() throws Exception {
		//when
		this.toTest.setUpValueProvisionChain();

		//then
		verify(this.booleanFieldValueProvider).setNextProvider(this.byteFieldValueProvider);
		verify(this.byteFieldValueProvider).setNextProvider(this.characterFieldValueProvider);
		verify(this.characterFieldValueProvider).setNextProvider(this.doubleFieldValueProvider);
		verify(this.doubleFieldValueProvider).setNextProvider(this.floatFieldValueProvider);
		verify(this.floatFieldValueProvider).setNextProvider(this.integerFieldValueProvider);
		verify(this.integerFieldValueProvider).setNextProvider(this.longFieldValueProvider);
		verify(this.longFieldValueProvider).setNextProvider(this.shortFieldValueProvider);
		verify(this.shortFieldValueProvider).setNextProvider(this.stringFieldValueProvider);
		verify(this.stringFieldValueProvider).setNextProvider(this.timestampFieldValueProvider);
		verify(this.timestampFieldValueProvider).setNextProvider(this.localeFieldValueProvider);
		verify(this.localeFieldValueProvider).setNextProvider(null);
		assertEquals(this.booleanFieldValueProvider, this.toTest.firstProvider);
	}

	@Test
	public void testThatProvideWorks() throws Exception {
		//given
		this.toTest.setUpValueProvisionChain();

		//when
		this.toTest.provide(randomStringValue, String.class);

		//then
		verify(this.booleanFieldValueProvider).provide(randomStringValue, String.class);
	}

	@Test
	public void testThatIncreaseWithFieldNameAndValueWorks() throws Exception {
		//given
		this.toTest.setUpValueProvisionChain();
		final String offsetValue = randomStringValue;

		//when
		this.toTest.offset(randomStringValue, offsetValue);

		//then
		verify(this.booleanFieldValueProvider).offset(randomStringValue, offsetValue);
	}

	@Test
	public void testThatIncreaseWithStepsWorks() throws Exception {
		//given
		this.toTest.setUpValueProvisionChain();
		final int steps = 10;

		//when
		this.toTest.increase(steps);

		//then
		verify(this.booleanFieldValueProvider).increase(steps);
	}

	@Test
	public void testThatInitWorks() throws Exception {
		//given
		this.toTest.setUpValueProvisionChain();
		final String initValue = randomStringValue;

		//when
		this.toTest.init(randomStringValue, initValue);

		//then
		verify(this.booleanFieldValueProvider).init(randomStringValue, initValue);
	}

	@Test
	public void testThatResetWorks() throws Exception {
		//given
		this.toTest.setUpValueProvisionChain();

		//when
		this.toTest.reset();

		//then
		verify(this.booleanFieldValueProvider).reset();
	}

	@Test
	public void testThatAddValueProviderWorks() throws Exception {
		//given
		this.toTest.setUpValueProvisionChain();

		//when
		this.toTest.addValueProvider(this.addedFieldValueProvider);

		//then
		verify(this.addedFieldValueProvider).setNextProvider(this.booleanFieldValueProvider);
		assertEquals(this.addedFieldValueProvider, this.toTest.firstProvider);
	}
}
