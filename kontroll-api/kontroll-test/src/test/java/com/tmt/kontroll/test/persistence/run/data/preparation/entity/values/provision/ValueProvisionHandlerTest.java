package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.array.ArrayValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl.StringValueProvider;

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
@PrepareForTest({ValueProvisionPreparer.class})
public class ValueProvisionHandlerTest {

	public static class Dummy {}

	@Mock
	private ValueProvisionPreparer valueProvisionHandlerPreparer;

	private ValueProvisionHandler toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(ValueProvisionPreparer.class);
		when(ValueProvisionPreparer.newInstance()).thenReturn(this.valueProvisionHandlerPreparer);
		this.toTest = ValueProvisionHandler.newInstance();
		this.toTest.addValueProvider(new ArrayValueProvider<>(String.class, this.toTest));
	}

	@Test
	public void testThatFetchValueProviderTypeWorks() throws Exception {
		//when
		final Class<? extends ValueProvider<?>> fetched = this.toTest.fetchValueProviderType(ValueProvisionKind.ZeroDimensional, Dummy.class, String.class);
		//then
		assertEquals(StringValueProvider.class, fetched);
	}

	@Test
	public void testThatCanProvideValueReturnsTrue() throws Exception {
		//when
		final boolean can = this.toTest.canProvideValue(ValueProvisionKind.ZeroDimensional, Dummy.class, String.class);
		//then
		assertTrue(can);
	}

	@Test
	public void testThatCanProvideValueReturnsFalse() throws Exception {
		//when
		final boolean can = this.toTest.canProvideValue(ValueProvisionKind.ZeroDimensional, this.getClass(), this.getClass());
		//then
		assertFalse(can);
	}

	@Test
	public void testThatProvideWorksForSimple() throws Exception {
		//when
		final Object provided = this.toTest.provide(ValueProvisionKind.ZeroDimensional, Dummy.class, String.class);
		//then
		assertEquals("0", provided);
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void testThatProvideWorksForCollection() throws Exception {
		//when
		final Object provided = this.toTest.provide(ValueProvisionKind.OneDimensional, Dummy.class, List.class, String.class);
		//then
		assertTrue(List.class.isAssignableFrom(provided.getClass()));
		assertTrue(((List) provided).size() == 1);
		assertEquals("0", ((List) provided).get(0));
	}

	@Test
	public void testThatProvideWorksForArray() throws Exception {
		//given
		//when
		final Object provided = this.toTest.provide(ValueProvisionKind.OneDimensional, Dummy.class, String[].class, String.class);
		//then
		assertTrue(String[].class.equals(provided.getClass()));
		assertTrue(((String[]) provided).length == 1);
		assertEquals("0", ((String[]) provided)[0]);
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void testThatProvideWorksForMap() throws Exception {
		//given
		//when
		final Object provided = this.toTest.provide(ValueProvisionKind.TwoDimensional, Dummy.class, Map.class, String.class, Integer.class);
		//then
		assertTrue(Map.class.isAssignableFrom(provided.getClass()));
		assertTrue(((Map) provided).size() == 1);
		assertEquals("0", ((Map) provided).keySet().iterator().next());
		assertEquals(0, ((Map) provided).values().iterator().next());
	}
}
