package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.HashMap;

import javax.persistence.Id;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.array.ArrayValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl.EnumMapValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.enumeration.EnumValueProviderFactory;
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
public class ValueProvisionHandlerPreparerTest {

	public enum DummyEnum {}

	public static class Dummy {
		@Id
		public String id;
		public String noId;
	}

	@Mock
	private ArrayValueProviderFactory arrayValueProviderFactory;
	@Mock
	private EnumValueProviderFactory enumValueProviderFactory;
	@Mock
	private IdValueProviderFactory idValueProviderFactory;
	@Mock
	private EnumMapValueProviderFactory enumMapValueProviderFactory;
	@Mock
	private ValueProvisionHandler valueProvisionHandler;

	private Field idField;
	private Field noIdField;

	private ValueProvisionHandlerPreparer toTest;

	@Before
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
		this.idField = Dummy.class.getDeclaredField("id");
		this.noIdField = Dummy.class.getDeclaredField("noId");
		this.toTest = ValueProvisionHandlerPreparer.newInstance();
	}

	@Test
	public void testThatPreparationWorksForArray() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, this.idField, new Dummy(), Dummy.class, String[].class, String.class);
		//then
		verify(this.arrayValueProviderFactory).create(this.valueProvisionHandler, String[].class);
		verify(this.enumValueProviderFactory, never()).create(this.valueProvisionHandler, DummyEnum.class);
		verify(this.idValueProviderFactory, never()).create(this.valueProvisionHandler, Dummy.class, DummyEnum.class);
		verify(this.enumMapValueProviderFactory, never()).create(this.valueProvisionHandler, DummyEnum.class);
	}

	@Test
	public void testThatPreparationWorksForEnum() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, this.idField, new Dummy(), Dummy.class, DummyEnum.class);
		//then
		verify(this.arrayValueProviderFactory, never()).create(this.valueProvisionHandler, String[].class);
		verify(this.enumValueProviderFactory).create(this.valueProvisionHandler, DummyEnum.class);
		verify(this.idValueProviderFactory, never()).create(this.valueProvisionHandler, Dummy.class, DummyEnum.class);
		verify(this.enumMapValueProviderFactory, never()).create(this.valueProvisionHandler, DummyEnum.class);
	}

	@Test
	public void testThatPreparationWorksForId() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, this.idField, new Dummy(), Dummy.class, String.class);
		//then
		verify(this.arrayValueProviderFactory, never()).create(this.valueProvisionHandler, String[].class);
		verify(this.enumValueProviderFactory, never()).create(this.valueProvisionHandler, DummyEnum.class);
		verify(this.idValueProviderFactory).create(this.valueProvisionHandler, Dummy.class, String.class);
		verify(this.enumMapValueProviderFactory, never()).create(this.valueProvisionHandler, DummyEnum.class);
	}

	@Test
	public void testThatPreparationWorksForEnumMap() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, this.idField, new Dummy(), Dummy.class, EnumMap.class, DummyEnum.class, String.class);
		//then
		verify(this.arrayValueProviderFactory, never()).create(this.valueProvisionHandler, String[].class);
		verify(this.enumValueProviderFactory, never()).create(this.valueProvisionHandler, DummyEnum.class);
		verify(this.idValueProviderFactory, never()).create(this.valueProvisionHandler, Dummy.class, String.class);
		verify(this.enumMapValueProviderFactory).create(this.valueProvisionHandler, DummyEnum.class);
	}

	@Test
	public void testThatPreparationDoesNotTreatEveryMapAsAnEnumMap() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, this.idField, new Dummy(), Dummy.class, HashMap.class, DummyEnum.class, String.class);
		//then
		verify(this.arrayValueProviderFactory, never()).create(this.valueProvisionHandler, String[].class);
		verify(this.enumValueProviderFactory, never()).create(this.valueProvisionHandler, DummyEnum.class);
		verify(this.idValueProviderFactory, never()).create(this.valueProvisionHandler, Dummy.class, String.class);
		verify(this.enumMapValueProviderFactory, never()).create(this.valueProvisionHandler, DummyEnum.class);
	}

	@Test
	public void testThatPreparationDoesNothingForStandardFields() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, this.noIdField, new Dummy(), Dummy.class, String.class);
		//then
		verify(this.arrayValueProviderFactory, never()).create(this.valueProvisionHandler, String[].class);
		verify(this.enumValueProviderFactory, never()).create(this.valueProvisionHandler, DummyEnum.class);
		verify(this.idValueProviderFactory, never()).create(this.valueProvisionHandler, Dummy.class, String.class);
		verify(this.enumMapValueProviderFactory, never()).create(this.valueProvisionHandler, DummyEnum.class);
	}

	@Test
	public void testThatPreparationDoesNothingIfNotArrayButThreeTypes() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, this.noIdField, new Dummy(), Dummy.class, String.class, String.class);
		//then
		verify(this.arrayValueProviderFactory, never()).create(this.valueProvisionHandler, String[].class);
		verify(this.enumValueProviderFactory, never()).create(this.valueProvisionHandler, DummyEnum.class);
		verify(this.idValueProviderFactory, never()).create(this.valueProvisionHandler, Dummy.class, String.class);
		verify(this.enumMapValueProviderFactory, never()).create(this.valueProvisionHandler, DummyEnum.class);
	}

	@Test(expected = RuntimeException.class)
	public void testThatPreparationThrowsException() throws Exception {
		//when
		this.toTest.prepare(this.valueProvisionHandler, this.noIdField, new Dummy(), Dummy.class, String.class, String.class, String.class, String.class);
	}
}
