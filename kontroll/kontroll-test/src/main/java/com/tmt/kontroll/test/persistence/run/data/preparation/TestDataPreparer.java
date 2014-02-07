package com.tmt.kontroll.test.persistence.run.data.preparation;

import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.IDataSet;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityUpdateProvider;
import com.tmt.kontroll.test.persistence.run.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;
import com.tmt.kontroll.test.persistence.run.exceptions.NoTestPreparerFoundException;

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
 * Furthermore, it ensures that the {@link EntityReferenceAsserter} used for the given test is configured
 * properly according to the {@link PersistenceTestConfig} of the current test method, e.g. that fields are
 * ignored if said so.
 * </p>
 * For more information on prepared test data, see {@link TestDataHolder}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public abstract class TestDataPreparer {

	private TestDataPreparer nextPreparer;

	protected abstract boolean isResponsible(final PersistenceTestConfig config);

	public void prepare(final PersistenceTestConfig config,
	                    final String entityClassName) throws Exception {
		if (this.isResponsible(config)) {
			this.prepareReferenceEntities(config, entityClassName);
			this.testDataSetBuilder().buildDataSetForSetup();
			this.testDataSetBuilder().buildDataSetForVerification();
			this.testDataSetBuilder().buildDataSetForTearDown();
			return;
		}
		if (this.nextPreparer == null) {
			throw NoTestPreparerFoundException.prepare(config, entityClassName);
		}
		this.nextPreparer.prepare(config, entityClassName);
	}

	/**
	 * Creates a list of {@link EntityReference} instances representing entities needed for the test run.
	 * The size of the list will depend on {@link PersistenceTestConfig#numberOfEntities}. The
	 * generated list will be delegated to {@link #prepareReferenceEntitiesForTestPhases}.
	 * 
	 * @param config
	 * @param entityClassName
	 * @throws Exception
	 */
	private void prepareReferenceEntities(final PersistenceTestConfig config,
	                                      final String entityClassName) throws Exception {
		final List<Object> entities = new ArrayList<>();
		for (int i = 0; i < config.numberOfEntities(); i++) {
			final Class<?> entityClass = Class.forName(entityClassName);
			this.configureReferenceAssertion(config, entityClass);
			final Object entity = this.entityInstanceProvider().provide(entityClass);
			entities.add(entity);
		}
		this.prepareReferenceEntitiesForSetup(config, entities);
		this.prepareReferenceEntitiesForRunning(config, entities);
		this.prepareReferenceEntitiesForVerification(config, entities);
		this.prepareReferenceEntitiesForTearDown(config, entities);
	}

	/**
	 * Prepares the reference entities for {@link TestPhase#Setup} by adding the plain
	 * entities as references to the {@link TestDataHolder}.
	 * </p>
	 * If a different behavior is
	 * wanted, for example tweaking entity values, it may be overridden in an implementing class.
	 * 
	 * @param config
	 * @param entities
	 */
	protected void prepareReferenceEntitiesForSetup(final PersistenceTestConfig config,
	                                                final List<Object> entities) {
		this.prepareReferenceEntitiesForTestPhase(TestPhase.Setup, entities);
	}

	/**
	 * Prepares the reference entities for {@link TestPhase#Running} by just adding the plain
	 * entities as references to the {@link TestDataHolder}.
	 * </p>
	 * If a different behavior is
	 * wanted, for example tweaking entity values, it may be overridden in an implementing class.
	 * 
	 * @param config
	 * @param entities
	 * @throws Exception
	 */
	protected void prepareReferenceEntitiesForRunning(final PersistenceTestConfig config,
	                                                  final List<Object> entities) throws Exception {
		this.prepareReferenceEntitiesForTestPhase(TestPhase.Running, entities);
	}

	/**
	 * Prepares the reference entities for {@link TestPhase#Verification} by just adding the plain
	 * entities as references to the {@link TestDataHolder}.
	 * </p>
	 * If a different behavior is
	 * wanted, for example tweaking entity values, it may be overridden in an implementing class.
	 * 
	 * @param config
	 * @param entities
	 */
	protected void prepareReferenceEntitiesForVerification(final PersistenceTestConfig config,
	                                                       final List<Object> entities) {
		this.prepareReferenceEntitiesForTestPhase(TestPhase.Verification, entities);
	}

	/**
	 * Prepares the reference entities for {@link TestPhase#TearDown} by just adding an empty
	 * reference list to the {@link TestDataHolder}, so that the database will be teared down
	 * with a complete but empty database schema.
	 * </p>
	 * If a different behavior is wanted, for example having certain data present after
	 * the test, it may be overridden in an implementing class.
	 * 
	 * @param config
	 * @param entities
	 */
	protected void prepareReferenceEntitiesForTearDown(final PersistenceTestConfig config,
	                                                   final List<Object> entities) {
		this.prepareReferenceEntitiesForTestPhase(TestPhase.TearDown, new ArrayList<Object>());
	}

	protected void prepareReferenceEntitiesForTestPhase(final TestPhase testPhase,
	                                                    final List<Object> entities) {
		final List<EntityReference> references = new ArrayList<>();
		for (final Object entity : entities) {
			references.add(new EntityReference(entity));
		}
		this.testDataHolder().addReferences(testPhase, references);
	}

	/**
	 * Creates and configures the {@link EntityReferenceAsserter} according to the configuration given in
	 * {@link PersistenceTestConfig}, e.g. which entity fields to ignore and such.
	 * </p>
	 * This might be overridden by an implementing class in order to achieve a different
	 * test behavior.
	 * </p>
	 * 
	 * @param entities
	 * @throws Exception
	 */
	protected void configureReferenceAssertion(final PersistenceTestConfig config,
	                                           final Class<?> entityClass) {
		final EntityReferenceAsserter asserter = this.referenceAsserter();
		asserter.configureForEntityId(config, entityClass);
		asserter.configureForIgnoredFields(config);
	}

	public void setNextPreparer(final TestDataPreparer nextPreparer) {
		this.nextPreparer = nextPreparer;
	}

	protected EntityInstanceProvider entityInstanceProvider() {
		return TestPreparationContext.instance().entityInstanceProvider();
	}

	protected EntityUpdateProvider entityUpdateProvider() {
		return TestPreparationContext.instance().entityUpdateProvider();
	}

	protected EntityReferenceAsserter referenceAsserter() {
		return TestPreparationContext.instance().referenceAsserter();
	}

	protected TestDataPreparer nextPreparer() {
		return this.nextPreparer;
	}

	protected TestDataHolder testDataHolder() {
		return TestPreparationContext.instance().testDataHolder();
	}

	protected TestDataSetBuilder testDataSetBuilder() {
		return TestPreparationContext.instance().testDataSetBuilder();
	}
}