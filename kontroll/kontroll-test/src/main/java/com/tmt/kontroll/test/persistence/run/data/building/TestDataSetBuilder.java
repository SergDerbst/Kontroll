package com.tmt.kontroll.test.persistence.run.data.building;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.builder.DataRowBuilder;
import org.dbunit.dataset.builder.DataSetBuilder;

import com.tmt.kontroll.persistence.utils.JpaEntityUtils;
import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.building.columns.TestDataSetColumnBuildingHandler;
import com.tmt.kontroll.test.persistence.run.data.building.compliance.TestDataComplianceAssurer;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;

/**
 * Builder to build {@link IDataSet}s for running prepared persistence tests. It builds three data
 * sets for three test phases:
 * <ul>
 * <li>for {@link TestPhase#Setup} the data set for setting up the database before the test is built</li>
 * <li>for {@link TestPhase#Verification} the data set for verifying the database at the end of the test is built</li>
 * <li>for {@link TestPhase#TearDown} the data set for tearing down the database after the test is built</li>
 * </ul>
 * </p>
 * The builder depends on proper references lists available at the {@link TestDataHolder} for the test
 * phases mentioned above. These have to be prepared by a {@link TestDataPreparer}.
 * </p>
 * The builder will assure that all data sets are always compliant with all managed entities within the persistence
 * context, meaning that all necessary data tables are present, including those for many-to-many relationships and
 * such. Therefore the references lists only require to contain the specific data needed for the test.
 * </p>
 * 
 * @author Sergio Weigel
 */
public class TestDataSetBuilder {

	private static class InstanceHolder {
		public static TestDataSetBuilder instance;
	}

	public static TestDataSetBuilder newInstance() throws Exception {
		InstanceHolder.instance = new TestDataSetBuilder();
		return InstanceHolder.instance;
	}

	DataSetBuilder builder;

	private TestDataSetBuilder() throws Exception {
		this.builder = new DataSetBuilder();
	}

	public void buildDataSetForSetup() throws Exception {
		this.testDataHolder().setDataSetForSetup(this.buildDataSet(this.testDataHolder().fetchReferences(TestPhase.Setup), false));
	}

	public void buildDataSetForVerification() throws Exception {
		this.testDataHolder().setDataSetForVerification(this.buildDataSet(this.testDataHolder().fetchReferences(TestPhase.Verification), true));
	}

	public void buildDataSetForTearDown() throws Exception {
		this.testDataHolder().setDataSetForTearDown(this.buildDataSet(this.testDataHolder().fetchReferences(TestPhase.TearDown), false));
	}

	private IDataSet buildDataSet(final Set<EntityReference> references,
	                              final boolean ignoreFields) throws Exception {
		for (final EntityReference reference : references) {
			final DataRowBuilder row = this.builder.newRow(reference.referenceType().getSimpleName());
			for (final Field field : retrievePropertyFields(reference.entity().getClass())) {
				if (this.fieldHasToBeIgnoredInDataSet(field, ignoreFields)) {
					continue;
				}
				this.testDataSetRowBuildingHandler().build(reference.entity(), field, row);
			}
			row.add();
		}
		this.tableComplianceAssurer().assureTableCompliance(this.builder);
		return this.builder.build();
	}

	private boolean fieldHasToBeIgnoredInDataSet(final Field field,
	                                             final boolean ignoreFields) {
		return
		this.isCollectionRelationshipField(field) ||
		(ignoreFields && this.referenceAsserter().ignoredFieldNames().contains(field.getName()));
	}

	private boolean isCollectionRelationshipField(final Field field) {
		return JpaEntityUtils.isRelationshipField(field) && Collection.class.isAssignableFrom(field.getType());
	}

	private EntityReferenceAsserter referenceAsserter() {
		return PersistenceTestContext.instance().entityReferenceAsserter();
	}

	private TestDataHolder testDataHolder() {
		return PersistenceTestContext.instance().testDataHolder();
	}

	private TestDataComplianceAssurer tableComplianceAssurer() {
		return PersistenceTestContext.instance().testDataComplianceAssurer();
	}

	private TestDataSetColumnBuildingHandler testDataSetRowBuildingHandler() {
		return PersistenceTestContext.instance().testDataSetColumnBuildingHandler();
	}
}
