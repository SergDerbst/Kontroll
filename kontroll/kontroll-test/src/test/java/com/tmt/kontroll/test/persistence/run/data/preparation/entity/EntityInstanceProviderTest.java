package com.tmt.kontroll.test.persistence.run.data.preparation.entity;


import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.List;
import java.util.Map;
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
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships.EntityRelationshipCollector;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

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
public class EntityInstanceProviderTest {

	public static class Dummy {
		@Id
		public int id = 1;
		public String dummyString;
		public String[] dummyStringArray;
		public List<String> dummyStringList;
		public Map<String, String> dummyStringMap;
		public int dummyInt;
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
	protected EntityRelationshipCollector entityRelationshipCollector;
	@Mock
	protected TestDataHolder testDataHolder;
	@Mock
	protected ValueProvisionHandler valueProvisionHandler;

	private EntityInstanceProvider toTest;

	@Before
	@SuppressWarnings("serial")
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(PersistenceTestContext.class);
		when(PersistenceTestContext.newInstance()).thenReturn(this.persistenceContext);
		when(PersistenceTestContext.instance()).thenReturn(this.persistenceContext);
		when(this.persistenceContext.entityReferenceAsserter()).thenReturn(this.entityReferenceAsserter);
		when(this.persistenceContext.entityRelationshipCollector()).thenReturn(this.entityRelationshipCollector);
		when(this.persistenceContext.testDataHolder()).thenReturn(this.testDataHolder);
		when(this.persistenceContext.valueProvisionHandler()).thenReturn(this.valueProvisionHandler);
		when(this.entityReferenceAsserter.ignoredFieldNames()).thenReturn(new TreeSet<String>(){{
			this.add("dummyInt");
		}});
		this.toTest = EntityInstanceProvider.newInstance();
	}

	@Test
	public void testThatProvideEntityReferencesWorks() throws Exception {
		//when
		this.toTest.provideEntityReferences(Dummy.class, TestStrategy.None);
		//then
		verify(this.entityRelationshipCollector).collect(Dummy.class, TestStrategy.None);
	}

	@Test
	@SuppressWarnings("serial")
	public void testThatProvideValuesWorks() throws Exception {
		//given
		final Dummy dummy = new Dummy();
		dummy.relatedDummy = new RelatedDummy();
		dummy.relatedDummy.myDummyOwnsMe = dummy;
		when(this.testDataHolder.allReferences()).thenReturn(new TreeSet<EntityReference>(new EntityReferenceComparator()){{
			this.add(new EntityReference(dummy, false, false));
		}});
		//when
		this.toTest.provideValues();
		//then
		verify(this.valueProvisionHandler).provide(dummy, Dummy.class.getDeclaredField("dummyString"), Dummy.class, String.class);
		verify(this.valueProvisionHandler).provide(dummy, Dummy.class.getDeclaredField("dummyStringArray"), Dummy.class, String[].class, String.class);
		verify(this.valueProvisionHandler).provide(dummy, Dummy.class.getDeclaredField("dummyStringList"), Dummy.class, List.class, String.class);
		verify(this.valueProvisionHandler).provide(dummy, Dummy.class.getDeclaredField("dummyStringMap"), Dummy.class, Map.class, String.class, String.class);
		verify(this.valueProvisionHandler, never()).provide(dummy, Dummy.class.getDeclaredField("id"), Dummy.class, Integer.class);
		verify(this.valueProvisionHandler, never()).provide(dummy, Dummy.class.getDeclaredField("dummyInt"), Dummy.class, Integer.class);
		verify(this.valueProvisionHandler, never()).provide(dummy, Dummy.class.getDeclaredField("relatedDummy"), Dummy.class, RelatedDummy.class);
	}

	@Test(expected = RuntimeException.class)
	public void testThatProvideValuesPacksExceptionsInRuntimeException() {
		//given
		when(this.testDataHolder.allReferences()).thenThrow(new Exception());
		//when
		this.toTest.provideValues();
	}
}
