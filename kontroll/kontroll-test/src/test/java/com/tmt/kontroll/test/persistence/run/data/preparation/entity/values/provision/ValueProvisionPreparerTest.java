package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.array.ArrayValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.array.ArrayValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl.EnumMapValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl.EnumMapValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.enumeration.EnumValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.enumeration.EnumValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.IdValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.IdValueProviderFactory;

/**
 * <b><i>Note:</i></b>
 * </br>
 * If you encounter a <code>java.lang.VerifyError</code> babbling about some inconsistent stackmap frames,
 * please make sure to follow these <a href="http://blog.triona.de/development/jee/how-to-use-powermock-with-java-7.html">instructions</a>.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
	ArrayValueProviderFactory.class,
	EnumValueProviderFactory.class,
	IdValueProviderFactory.class,
	EnumMapValueProviderFactory.class
})
public class ValueProvisionPreparerTest {

	public enum DummyEnum {}

	public static class Dummy {}

	@Mock
	private ArrayValueProviderFactory arrayValueProviderFactory;
	@Mock
	private ArrayValueProvider<?> arrayValueProvider;
	@Mock
	private EnumValueProviderFactory enumValueProviderFactory;
	@Mock
	private EnumValueProvider enumValueProvider;
	@Mock
	private IdValueProviderFactory idValueProviderFactory;
	@Mock
	@SuppressWarnings("rawtypes")
	private IdValueProvider idValueProvider;
	@Mock
	private EnumMapValueProviderFactory enumMapValueProviderFactory;
	@Mock
	private EnumMapValueProvider<? extends Enum<?>> enumMapValueProvider;
	@Mock
	private ValueProvisionHandler valueProvisionHandler;

	private ValueProvisionPreparer toTest;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(ArrayValueProviderFactory.class);
		mockStatic(EnumValueProviderFactory.class);
		mockStatic(IdValueProviderFactory.class);
		mockStatic(EnumMapValueProviderFactory.class);
		when(ArrayValueProviderFactory.instance()).thenReturn(this.arrayValueProviderFactory);
		when(EnumValueProviderFactory.instance()).thenReturn(this.enumValueProviderFactory);
		when(IdValueProviderFactory.instance()).thenReturn(this.idValueProviderFactory);
		when(EnumMapValueProviderFactory.instance()).thenReturn(this.enumMapValueProviderFactory);
		when(this.arrayValueProviderFactory.create(eq(this.valueProvisionHandler), any(Class.class))).thenReturn(this.arrayValueProvider);
		when(this.enumValueProviderFactory.create(eq(this.valueProvisionHandler), any(Class.class))).thenReturn(this.enumValueProvider);
		when(this.idValueProviderFactory.create(eq(this.valueProvisionHandler), any(Class.class), any(Class.class))).thenReturn(this.idValueProvider);
		when(this.enumMapValueProviderFactory.create(eq(this.valueProvisionHandler), any(Class.class))).thenReturn(this.enumMapValueProvider);
		when(this.valueProvisionHandler.canProvideValue(any(ValueProvisionKind.class), any(Class[].class))).thenReturn(false);
		this.toTest = ValueProvisionPreparer.newInstance();
	}

	@Test
	public void testThatValuePreparationWorksForArray() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, ValueProvisionKind.OneDimensional, new Dummy(), Dummy.class, String[].class, String.class);
		//then
		verify(this.arrayValueProviderFactory).create(this.valueProvisionHandler, String.class);
		verify(this.valueProvisionHandler).addValueProvider(this.arrayValueProvider);
	}

	@Test
	public void testThatValuePreparationWorksForCollection() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, ValueProvisionKind.OneDimensional, new Dummy(), Dummy.class, List.class, String.class);
		//then
		verify(this.valueProvisionHandler).canProvideValue(ValueProvisionKind.OneDimensional, Dummy.class, List.class, String.class);
		verify(this.valueProvisionHandler).canProvideValue(null, Dummy.class, String.class);
	}

	@Test
	public void testThatValuePreparationWorksForId() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, ValueProvisionKind.Id, new Dummy(), Dummy.class, String.class);
		//then
		verify(this.idValueProviderFactory).create(this.valueProvisionHandler, Dummy.class, String.class);
		verify(this.valueProvisionHandler).addValueProvider(this.idValueProvider);
	}

	@Test
	public void testThatValuePreparationWorksForIdIsOmittedWhenFieldIsNull() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, null, new Dummy(), Dummy.class, String.class);
		//then
		verify(this.idValueProviderFactory, never()).create(this.valueProvisionHandler, Dummy.class, String.class);
	}

	@Test
	public void testThatValuePreparationWorksForEnum() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, ValueProvisionKind.ZeroDimensional, new Dummy(), Dummy.class, DummyEnum.class);
		//then
		verify(this.enumValueProviderFactory).create(this.valueProvisionHandler, DummyEnum.class);
		verify(this.valueProvisionHandler).addValueProvider(this.enumValueProvider);
	}

	@Test
	public void testThatValuePreparationWorksForEnumWhenFieldIsNull() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, ValueProvisionKind.ZeroDimensional, new Dummy(), Dummy.class, DummyEnum.class);
		//then
		verify(this.enumValueProviderFactory).create(this.valueProvisionHandler, DummyEnum.class);
		verify(this.valueProvisionHandler).addValueProvider(this.enumValueProvider);
	}

	@Test
	public void testThatValuePreparationWorksForEnumMap() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, ValueProvisionKind.TwoDimensional, new Dummy(), Dummy.class, EnumMap.class, DummyEnum.class, String.class);
		//then
		verify(this.enumMapValueProviderFactory).create(this.valueProvisionHandler, DummyEnum.class);
		verify(this.valueProvisionHandler).addValueProvider(this.enumMapValueProvider);
	}

	@Test
	public void testThatValuePreparationWorksForMap() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, ValueProvisionKind.TwoDimensional, new Dummy(), Dummy.class, Map.class, String.class, Integer.class);
		//then
		verify(this.valueProvisionHandler).canProvideValue(ValueProvisionKind.TwoDimensional, Dummy.class, Map.class, String.class, Integer.class);
		verify(this.valueProvisionHandler).canProvideValue(null, Dummy.class, String.class);
		verify(this.valueProvisionHandler).canProvideValue(null, Dummy.class, Integer.class);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testThatValuePreparationWorksForStandardField() throws Exception {
		//given
		when(this.valueProvisionHandler.canProvideValue(any(ValueProvisionKind.class), any(Class.class))).thenReturn(true);
		//when
		this.toTest.prepare(this.valueProvisionHandler, ValueProvisionKind.ZeroDimensional, new Dummy(), Dummy.class, String.class);
		//then
		verify(this.valueProvisionHandler).canProvideValue(ValueProvisionKind.ZeroDimensional, Dummy.class, String.class);
		verify(this.arrayValueProviderFactory, never()).create(eq(this.valueProvisionHandler), any(Class.class));
		verify(this.idValueProviderFactory, never()).create(eq(this.valueProvisionHandler), any(Class.class), any(Class.class));
		verify(this.enumValueProviderFactory, never()).create(eq(this.valueProvisionHandler), any(Class.class));
		verify(this.enumMapValueProviderFactory, never()).create(eq(this.valueProvisionHandler), any(Class.class));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testThatValuePreparationWorksForStandardFieldWhenFieldIsNull() throws Exception {
		//given
		when(this.valueProvisionHandler.canProvideValue(any(ValueProvisionKind.class), any(Class.class))).thenReturn(true);
		//when
		this.toTest.prepare(this.valueProvisionHandler, null, new Dummy(), Dummy.class, String.class);
		//then
		verify(this.valueProvisionHandler).canProvideValue(null, Dummy.class, String.class);
		verify(this.arrayValueProviderFactory, never()).create(eq(this.valueProvisionHandler), any(Class.class));
		verify(this.idValueProviderFactory, never()).create(eq(this.valueProvisionHandler), any(Class.class), any(Class.class));
		verify(this.enumValueProviderFactory, never()).create(eq(this.valueProvisionHandler), any(Class.class));
		verify(this.enumMapValueProviderFactory, never()).create(eq(this.valueProvisionHandler), any(Class.class));
	}
}
