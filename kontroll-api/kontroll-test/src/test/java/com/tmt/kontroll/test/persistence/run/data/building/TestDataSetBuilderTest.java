package com.tmt.kontroll.test.persistence.run.data.building;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.builder.DataRowBuilder;
import org.dbunit.dataset.builder.DataSetBuilder;
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
import com.tmt.kontroll.test.persistence.run.data.building.columns.TestDataSetColumnBuildingHandler;
import com.tmt.kontroll.test.persistence.run.data.building.compliance.TestDataComplianceAssurer;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;

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
public class TestDataSetBuilderTest {

	public static class Dummy {
		public String neverIgnored;
		@OneToOne
		public String sometimesIgnored;
		public List<String> sometimesIgnoredList;
		@ManyToMany
		public List<String> alwaysIgnored;
	}

	@Mock
	private PersistenceTestContext persistenceContext;
	@Mock
	private TestDataSetColumnBuildingHandler testDataSetColumnBuildingHandler;
	@Mock
	private TestDataComplianceAssurer testDataComplianceAssurer;
	@Mock
	private TestDataHolder testDataHolder;
	@Mock
	private EntityReferenceAsserter entityReferenceAsserter;
	@Mock
	private EntityReference entityReference;
	@Mock
	private DataSetBuilder dataSetBuilder;
	@Mock
	private DataRowBuilder dataRowBuilder;
	@Mock
	private IDataSet dataSet;

	private Set<EntityReference> references;

	private TestDataSetBuilder toTest;

	@Before
	@SuppressWarnings({"serial", "rawtypes", "unchecked"})
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(PersistenceTestContext.class);
		when(PersistenceTestContext.newInstance()).thenReturn(this.persistenceContext);
		when(PersistenceTestContext.instance()).thenReturn(this.persistenceContext);
		when(this.persistenceContext.entityReferenceAsserter()).thenReturn(this.entityReferenceAsserter);
		when(this.persistenceContext.testDataComplianceAssurer()).thenReturn(this.testDataComplianceAssurer);
		when(this.persistenceContext.testDataHolder()).thenReturn(this.testDataHolder);
		when(this.persistenceContext.testDataSetColumnBuildingHandler()).thenReturn(this.testDataSetColumnBuildingHandler);
		this.references = new HashSet<EntityReference>() {{
			this.add(TestDataSetBuilderTest.this.entityReference);
		}};
		when(this.testDataHolder.fetchReferences(any(TestPhase.class))).thenReturn(this.references);
		when(this.entityReference.entity()).thenReturn(new Dummy());
		when(this.entityReference.referenceType()).thenReturn((Class) Dummy.class);
		when(this.dataSetBuilder.newRow(any(String.class))).thenReturn(this.dataRowBuilder);
		when(this.dataSetBuilder.build()).thenReturn(this.dataSet);
		when(this.entityReferenceAsserter.ignoredFieldNames()).thenReturn(new HashSet<String>() {{
			this.add("sometimesIgnored");
			this.add("sometimesIgnoredList");
		}});
		this.toTest = TestDataSetBuilder.newInstance();
		this.toTest.builder = this.dataSetBuilder;
	}

	@Test
	public void testThatBuildDataSetForSetupWorks() throws Exception {
		//when
		this.toTest.buildDataSetForSetup();
		//then
		verify(this.testDataSetColumnBuildingHandler).build(any(Dummy.class), eq(Dummy.class.getDeclaredField("neverIgnored")), any(DataRowBuilder.class));
		verify(this.testDataSetColumnBuildingHandler).build(any(Dummy.class), eq(Dummy.class.getDeclaredField("sometimesIgnored")), any(DataRowBuilder.class));
		verify(this.testDataSetColumnBuildingHandler).build(any(Dummy.class), eq(Dummy.class.getDeclaredField("sometimesIgnoredList")), any(DataRowBuilder.class));
		verify(this.testDataSetColumnBuildingHandler, never()).build(any(Dummy.class), eq(Dummy.class.getDeclaredField("alwaysIgnored")), any(DataRowBuilder.class));
		verify(this.testDataComplianceAssurer).assureTableCompliance(this.dataSetBuilder);
	}

	@Test
	public void testThatBuildDataSetForTearDownWorks() throws Exception {
		//when
		this.toTest.buildDataSetForTearDown();
		//then
		verify(this.testDataSetColumnBuildingHandler).build(any(Dummy.class), eq(Dummy.class.getDeclaredField("neverIgnored")), any(DataRowBuilder.class));
		verify(this.testDataSetColumnBuildingHandler).build(any(Dummy.class), eq(Dummy.class.getDeclaredField("sometimesIgnored")), any(DataRowBuilder.class));
		verify(this.testDataSetColumnBuildingHandler).build(any(Dummy.class), eq(Dummy.class.getDeclaredField("sometimesIgnoredList")), any(DataRowBuilder.class));
		verify(this.testDataSetColumnBuildingHandler, never()).build(any(Dummy.class), eq(Dummy.class.getDeclaredField("alwaysIgnored")), any(DataRowBuilder.class));
		verify(this.testDataComplianceAssurer).assureTableCompliance(this.dataSetBuilder);
	}

	@Test
	public void testThatBuildDataSetForVerificationWorks() throws Exception {
		//when
		this.toTest.buildDataSetForVerification();
		//then
		verify(this.testDataSetColumnBuildingHandler).build(any(Dummy.class), eq(Dummy.class.getDeclaredField("neverIgnored")), any(DataRowBuilder.class));
		verify(this.testDataSetColumnBuildingHandler, never()).build(any(Dummy.class), eq(Dummy.class.getDeclaredField("sometimesIgnored")), any(DataRowBuilder.class));
		verify(this.testDataSetColumnBuildingHandler, never()).build(any(Dummy.class), eq(Dummy.class.getDeclaredField("sometimesIgnoredList")), any(DataRowBuilder.class));
		verify(this.testDataSetColumnBuildingHandler, never()).build(any(Dummy.class), eq(Dummy.class.getDeclaredField("alwaysIgnored")), any(DataRowBuilder.class));
		verify(this.testDataComplianceAssurer).assureTableCompliance(this.dataSetBuilder);
	}
}
