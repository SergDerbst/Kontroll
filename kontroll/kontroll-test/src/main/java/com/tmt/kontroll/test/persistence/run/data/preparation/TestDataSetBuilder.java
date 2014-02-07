package com.tmt.kontroll.test.persistence.run.data.preparation;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionHelper.retrievePropertyFields;

import java.lang.reflect.Field;
import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.builder.ColumnSpec;
import org.dbunit.dataset.builder.DataRowBuilder;
import org.dbunit.dataset.builder.DataSetBuilder;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.enums.TestPhase;

/**
 * Builder to build {@link IDataSet}s for running prepared persistence tests. It builds three data
 * sets for three test phases:
 * <ul>
 * <li>for {@link TestPhase#Setup} the data set for setting up the database before the test is built</li>
 * <li>for {@link TestPhase#Verification} the data set for verifying the database at the end of the test is built</li>
 * <li>for {@link TestPhase#TearDown} the data set tearing down the database after the test is built</li>
 * </ul>
 * </p>
 * The builder depends on proper references lists available from the {@link TestDataHolder} for the test
 * phases mentioned above. These have to be prepared by a {@link TestDataPreparer}.
 * </p>
 * It will also assure that the data sets are compliant with all managed entities within the persistence
 * context, meaning that all necessary data tables, including those for many-to-many relationships and
 * such, are present. Thus, the references only require to contain the specific data needed for the test.
 * </p>
 * 
 * @author Serg Derbst
 */
public class TestDataSetBuilder {

	private static class InstanceHolder {
		public static TestDataSetBuilder instance;
	}

	public static TestDataSetBuilder newInstance() {
		InstanceHolder.instance = new TestDataSetBuilder();
		return InstanceHolder.instance;
	}

	private TestDataSetBuilder() {}

	public void buildDataSetForSetup() throws Exception {
		this.testDataHolder().setDataSetForSetup(this.buildDataSet(this.testDataHolder().getReferences(TestPhase.Setup), false));
	}

	public void buildDataSetForVerification() throws Exception {
		this.testDataHolder().setDataSetForVerification(this.buildDataSet(this.testDataHolder().getReferences(TestPhase.Verification), true));
	}

	public void buildDataSetForTearDown() throws Exception {
		this.testDataHolder().setDataSetForTearDown(this.buildDataSet(this.testDataHolder().getReferences(TestPhase.TearDown), false));
	}

	private IDataSet buildDataSet(final List<EntityReference> references,
	                              final boolean ignoreFields) throws Exception {
		final DataSetBuilder builder = new DataSetBuilder();
		for (final EntityReference reference : references) {
			final DataRowBuilder row = builder.newRow(reference.getEntity().getClass().getSimpleName());
			for (final Field field : retrievePropertyFields(reference.getEntity().getClass())) {
				if (this.fieldHasToBeIgnoredInDataSet(field, ignoreFields)) {
					continue;
				}
				field.setAccessible(true);
				row.with(ColumnSpec.newColumn(field.getName()), field.get(reference.getEntity()));
			}
			row.add();
		}
		this.tableComplianceAssurer().assureTableCompliance(builder);
		return builder.build();
	}

	private boolean fieldHasToBeIgnoredInDataSet(final Field field,
	                                             final boolean ignoreFields) {
		return ignoreFields && this.referenceAsserter().getIgnoredFieldNames().contains(field.getName());
	}

	private EntityReferenceAsserter referenceAsserter() {
		return TestPreparationContext.instance().referenceAsserter();
	}

	private TestDataHolder testDataHolder() {
		return TestPreparationContext.instance().testDataHolder();
	}

	private TestDataComplianceAssurer tableComplianceAssurer() {
		return TestPreparationContext.instance().testDataComplianceAssurer();
	}
}
