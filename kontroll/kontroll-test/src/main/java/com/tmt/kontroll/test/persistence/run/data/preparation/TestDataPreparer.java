package com.tmt.kontroll.test.persistence.run.data.preparation;

import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.retrieveFieldsForValueProvision;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.builder.ColumnSpec;
import org.dbunit.dataset.builder.DataRowBuilder;
import org.dbunit.dataset.builder.DataSetBuilder;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.compliance.TableComplianceAssurer;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.run.data.reference.Reference;
import com.tmt.kontroll.test.persistence.run.data.reference.ReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.exceptions.NoTestPreparerFoundException;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

/**
 * Responsibility chain element to prepare test data according to the proper {@link TestStrategy}.
 * </p>
 * It prepares three things:
 * <ul>
 * <li>an {@link IDataSet} consisting of entities that have to exist in the data base before the test</li>
 * <li>a list of entities as references for the test itself</li>
 * <li>an {@link IDataSet} consisting of entities that represent the expected state of the data base after the test.</li>
 * </ul>
 * </p>
 * Furthermore it will assure that datasets are compliant with all managed entities, meaning
 * that all necessary data tables, including those for many-to-many relationships, are present.
 * </p>
 * It also ensures that the {@link ReferenceAsserter} used for the given test is configured
 * properly according to the {@link PersistenceTestConfig} of the current test method.
 * </p>
 * For more information on prepared test data, see {@link TestDataHolder}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
@Component
public abstract class TestDataPreparer {

	private TestDataPreparer nextPreparer;

	protected abstract boolean isResponsible(final PersistenceTestConfig config);

	public void prepare(final PersistenceTestConfig config,
	                    final String entityClassName) throws Exception {
		if (this.isResponsible(config)) {
			this.prepareDataSets(this.prepareReference(config, entityClassName));
			return;
		}
		if (this.nextPreparer == null) {
			throw NoTestPreparerFoundException.prepare(config, entityClassName);
		}
		this.nextPreparer.prepare(config, entityClassName);
	}

	/**
	 * This method might be overridden by an implementing class
	 * in order to achieve the wanted test behavior.
	 * 
	 * @param entities
	 * @throws Exception
	 */
	protected void prepareDataSets(final List<Object> entities) throws Exception {
		this.getTestDataHolder().setDataSetBefore(this.buildDataSetBefore(entities));
		this.getTestDataHolder().setDataSetAfter(this.buildDataSetAfter(entities));
	}

	/**
	 * This method might be overridden by an implementing class
	 * in order to achieve the wanted test behavior.
	 * 
	 * @param entities
	 * @throws Exception
	 */
	protected IDataSet buildDataSetAfter(final List<Object> entities) throws Exception {
		return this.buildDataSet(entities, true);
	}

	/**
	 * This method might be overridden by an implementing class
	 * in order to achieve the wanted test behavior.
	 * 
	 * @param entities
	 * @throws Exception
	 */
	protected IDataSet buildDataSetBefore(final List<Object> entities) throws Exception {
		return this.buildDataSet(entities, false);
	}

	private IDataSet buildDataSet(final List<Object> entities,
	                              final boolean ignoreFields) throws Exception {
		final DataSetBuilder builder = new DataSetBuilder();
		for (final Object entity : entities) {
			final DataRowBuilder row = builder.newRow(entity.getClass().getSimpleName());
			for (final Field field : retrieveFieldsForValueProvision(entity.getClass())) {
				if (this.fieldHasToBeIgnoredInDataSet(field, ignoreFields)) {
					continue;
				}
				field.setAccessible(true);
				row.with(ColumnSpec.newColumn(field.getName()), field.get(entity));
			}
			row.add();
		}
		this.getTableComplianceAssurer().assureTableCompliance(builder);
		return builder.build();
	}

	private boolean fieldHasToBeIgnoredInDataSet(final Field field,
	                                             final boolean ignoreFields) {
		return ignoreFields && this.getTestDataHolder().getReferenceAsserter().getIgnoredFieldNames().contains(field.getName());
	}

	/**
	 * This method might be overridden by an implementing class
	 * in order to achieve the wanted test behavior.
	 * 
	 * @param entities
	 * @throws Exception
	 */
	protected List<Object> prepareReference(final PersistenceTestConfig config,
	                                        final String entityClassName) throws Exception {
		final List<Object> entities = new ArrayList<>();
		for (int i = 0; i < config.numberOfEntities(); i++) {
			final Class<?> entityClass = Class.forName(entityClassName);
			this.prepareReferenceAssertion(config, entityClass);
			final Object entity = this.getEntityInstanceProvider().provide(entityClass);
			this.getTestDataHolder().addReference(new Reference(entity));
			entities.add(entity);
		}
		return entities;
	}

	/**
	 * This method might be overridden by an implementing class
	 * in order to achieve the wanted test behavior.
	 * 
	 * @param entities
	 * @throws Exception
	 */
	protected void prepareReferenceAssertion(final PersistenceTestConfig config,
	                                         final Class<?> entityClass) {
		final ReferenceAsserter asserter = new ReferenceAsserter();
		asserter.configureForEntityId(config, entityClass);
		asserter.configureForIgnoredFields(config);
		this.getTestDataHolder().setReferenceAsserter(asserter);
	}

	public void setNextPreparer(final TestDataPreparer nextPreparer) {
		this.nextPreparer = nextPreparer;
	}

	protected TestDataHolder getTestDataHolder() {
		return TestDataHolder.instance();
	}

	protected TableComplianceAssurer getTableComplianceAssurer() {
		return TableComplianceAssurer.instance();
	}

	protected EntityInstanceProvider getEntityInstanceProvider() {
		return EntityInstanceProvider.instance();
	}
}