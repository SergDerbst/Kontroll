package com.tmt.kontroll.content.persistence.conversion;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.content.persistence.conversion.impl.BooleanStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.ByteStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.CharacterStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.DoubleStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.FloatStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.IntegerStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.LongStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.ShortStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.StringStringConverter;

@RunWith(MockitoJUnitRunner.class)
public class ValueStringConversionChainTest {
	
	@Mock
	private BooleanStringConverter booleanStringConverter;
	@Mock
	private ByteStringConverter byteStringConverter;
	@Mock
	private CharacterStringConverter characterStringConverter;
	@Mock
	private DoubleStringConverter doubleStringConverter;
	@Mock
	private FloatStringConverter floatStringConverter;
	@Mock
	private IntegerStringConverter integerStringConverter;
	@Mock
	private LongStringConverter longStringConverter;
	@Mock
	private ShortStringConverter shortStringConverter;
	@Mock
	private StringStringConverter stringStringConverter;
	
	private ValueStringConversionChain toTest;

	@Before
	public void setUp() throws Exception {
		this.toTest = new ValueStringConversionChain();
		this.toTest.booleanStringConverter = this.booleanStringConverter;
		this.toTest.byteStringConverter = this.byteStringConverter;
		this.toTest.characterStringConverter = this.characterStringConverter;
		this.toTest.doubleStringConverter = this.doubleStringConverter;
		this.toTest.floatStringConverter = this.floatStringConverter;
		this.toTest.integerStringConverter = this.integerStringConverter;
		this.toTest.longStringConverter = this.longStringConverter;
		this.toTest.shortStringConverter = this.shortStringConverter;
		this.toTest.stringStringConverter = this.stringStringConverter;
	}

	@Test
	public void testThatConversionChainIsSetUpProperly() throws Exception {
		//when
		this.toTest.setUpConversionChain();
		
		//then
		verify(this.stringStringConverter).setNextConverter(this.integerStringConverter);
		verify(this.integerStringConverter).setNextConverter(this.booleanStringConverter);
		verify(this.booleanStringConverter).setNextConverter(this.longStringConverter);
		verify(this.longStringConverter).setNextConverter(this.doubleStringConverter);
		verify(this.doubleStringConverter).setNextConverter(this.floatStringConverter);
		verify(this.floatStringConverter).setNextConverter(this.shortStringConverter);
		verify(this.shortStringConverter).setNextConverter(this.byteStringConverter);
		verify(this.byteStringConverter).setNextConverter(this.characterStringConverter);
		verify(this.characterStringConverter).setNextConverter(null);
	}

	@Test
	public void testThatConvertToStringStartsChainWithStringConverter() throws Exception {
		//given
		final Integer value = new Integer(0);
		
		//when
		this.toTest.convertToString(value);
		
		//then
		verify(this.stringStringConverter).convertToString(value);
	}

	@Test
	public void testThatConvertToValueStartsChainWithStringConverter() throws Exception {
	//given
			final String string = "blubb";
			
			//when
			this.toTest.convertToValue(string, this.getClass());
			
			//then
			verify(this.stringStringConverter).convertToValue(string, this.getClass());
	}
}
