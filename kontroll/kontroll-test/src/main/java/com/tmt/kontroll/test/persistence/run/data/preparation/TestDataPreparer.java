package com.tmt.kontroll.test.persistence.run.data.preparation;

import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.retrieveFieldsForValueProvision;

import java.lang.reflect.Field;
import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.builder.ColumnSpec;
import org.dbunit.dataset.builder.DataRowBuilder;
import org.dbunit.dataset.builder.DataSetBuilder;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.DataSetHolder;
import com.tmt.kontroll.test.persistence.run.data.preparation.compliance.TableComplianceAssurer;
import com.tmt.kontroll.test.persistence.run.data.reference.ReferenceHolder;
import com.tmt.kontroll.test.persistence.run.exceptions.NoTestPreparerFoundException;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

/**
 * Strategy type to prepare test data according to the proper {@link TestStrategy}.
 * It prepares three sets of entities:
 * <ul>
 * <li>entities that have to exist in the data base before the test</li>
 * <li>entities as references for the test itself</li>
 * <li>entities that represent the expected state of the data base after the test</li>
 * </ul>
 * </p>
 * Furthermore it will assure that datasets are compliant with all managed entities, meaning
 * that all necessary data tables, including those for many-to-many relationships, are present.
 * 
 * @author Serg Derbst
 *
 */
@Component
public abstract class TestDataPreparer {

	private TestDataPreparer nextPreparer;

	protected abstract boolean isResponsible(final PersistenceTestConfig config);

	protected abstract void doPreparation(final PersistenceTestConfig config, final String entityClassName) throws Exception;

	public void prepare(final PersistenceTestConfig config, final String entityClassName) throws Exception {
		if (this.isResponsible(config)) {
			this.getReferenceHolder().clearReferences();
			this.doPreparation(config, entityClassName);
			return;
		}
		if (this.nextPreparer == null) {
			throw NoTestPreparerFoundException.prepare(config, entityClassName);
		}
		this.nextPreparer.prepare(config, entityClassName);
	}

	protected IDataSet buildDataSet(final List<Object> entities) throws Exception {
		final DataSetBuilder builder = new DataSetBuilder();
		for (final Object entity : entities) {
			final DataRowBuilder row = builder.newRow(entity.getClass().getSimpleName());
			for (final Field field : retrieveFieldsForValueProvision(entity.getClass())) {
				field.setAccessible(true);
				row.with(ColumnSpec.newColumn(field.getName()), field.get(entity));
			}
			row.add();
		}
		this.getTableComplianceAssurer().assureTableCompliance(builder);
		return builder.build();
	}

	public void setNextPreparer(final TestDataPreparer nextPreparer) {
		this.nextPreparer = nextPreparer;
	}

	protected TableComplianceAssurer getTableComplianceAssurer() {
		return TableComplianceAssurer.instance();
	}

	protected DataSetHolder getDataSetHolder() {
		return DataSetHolder.instance();
	}

	protected ReferenceHolder getReferenceHolder() {
		return ReferenceHolder.instance();
	}

	protected EntityInstanceProvider getEntityInstanceProvider() {
		return EntityInstanceProvider.instance();
	}
}