package com.tmt.kontroll.test.persistence.run.data.preparation.entity;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.TreeSet;

import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

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
@PrepareForTest({PersistenceTestContext.class})
public class EntityUpdateProviderTest {

	public static class Dummy {
		@Id
		public int id = 1;
		public String dummyString ="wurst";
		public int dummyInt = 0;
		@OneToOne
		public RelatedDummy relatedDummy;
	}

	public static class RelatedDummy {
		@OneToOne(mappedBy = "relatedDummy")
		public Dummy myDummyOwnsMe;
	}

	@Mock
	protected PersistenceTestContext persistenceContext;
	@Mock
	protected EntityReferenceAsserter entityReferenceAsserter;
	@Mock
	protected ValueProvisionHandler valueProvisionHandler;

	private EntityUpdateProvider toTest;

	@Before
	@SuppressWarnings("serial")
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(PersistenceTestContext.class);
		when(PersistenceTestContext.newInstance()).thenReturn(this.persistenceContext);
		when(PersistenceTestContext.instance()).thenReturn(this.persistenceContext);
		when(this.persistenceContext.valueProvisionHandler()).thenReturn(this.valueProvisionHandler);
		when(this.persistenceContext.entityReferenceAsserter()).thenReturn(this.entityReferenceAsserter);
		when(this.entityReferenceAsserter.ignoredFieldNames()).thenReturn(new TreeSet<String>(){{
			this.add("dummyInt");
		}});
		this.toTest = EntityUpdateProvider.newInstance();
	}

	@Test
	public void testThatProvideNewUpdatedWorks() throws Exception {
		//given
		final EntityReference reference = new EntityReference(new Dummy(), true, false);
		//when
		final EntityReference updated = this.toTest.provideNewUpdated(reference);
		//then
		assertNotNull(updated);
		assertFalse(updated == reference);
		assertFalse(updated.entity() == reference.entity());
		assertEquals(new Integer(((Dummy) reference.entity()).id), new Integer(((Dummy) updated.entity()).id));
		verify(this.valueProvisionHandler).provideNextZeroDimensionalValue(any(Dummy.class), eq("wurst"));
		verify(this.valueProvisionHandler, never()).provideNextZeroDimensionalValue(any(Dummy.class), eq(0));
		verify(this.valueProvisionHandler, never()).provideNextZeroDimensionalValue(eq(reference.entity()), any(RelatedDummy.class));
	}

	@Test(expected = RuntimeException.class)
	public void testThatProvideNewUpdatedPacksExceptionsIntoRuntimeExceptions() throws Exception {
		//given
		when(this.entityReferenceAsserter.ignoredFieldNames()).thenThrow(new Exception());
		final EntityReference reference = new EntityReference(new Dummy(), true, false);
		//when
		this.toTest.provideNewUpdated(reference);
	}
}