package com.tmt.kontroll.test.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.tmt.kontroll.persistence.daos.CrudDao;
import com.tmt.kontroll.test.persistence.run.KontrollDbUnitTestExecutionListener;
import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintAsserter;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.LengthConstraintAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.NullableConstraintAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.UniqueConstraintOnColumnAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.UniqueConstraintOnTableAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.impl.LengthConstraintAssertionFailure;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.impl.NullableConstraintAssertionFailure;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.impl.UniqueConstraintOnColumnAssertionFailure;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.impl.UniqueConstraintOnTableAssertionFailure;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestPreparationContext;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityUpdateProvider;
import com.tmt.kontroll.test.persistence.run.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class, KontrollDbUnitTestExecutionListener.class})
@TransactionConfiguration(defaultRollback = false)
public abstract class PersistenceEntityDaoServiceTest<Entity extends Object, ID extends Serializable, REPO extends JpaRepository<Entity, ID>, S extends CrudDao<Entity, ID>> {

	protected abstract S daoService();

	public abstract void test_save() throws Exception;

	public abstract void test_saveAll() throws Exception;

	public abstract void test_update() throws Exception;

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Count, numberOfEntities = 2)
	public void test_count() {
		//when
		assertEquals(new Long(2), (Long) this.daoService().count());
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Delete)
	@SuppressWarnings("unchecked")
	public void test_delete() throws Exception {
		//given
		final ID id = (ID) this.fetchReferences().get(0).getReferenceValue("id");
		//when
		this.daoService().delete(id);
		//then
		assertEquals(new Long(0), (Long) this.daoService().count());
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Exists)
	@SuppressWarnings("unchecked")
	public void test_exists() throws Exception {
		//given
		final ID id = (ID) this.fetchReferences().get(0).getReferenceValue("id");
		//when
		final boolean exists = this.daoService().exists(id);
		//then
		assertTrue(exists);
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Find, numberOfEntities = 2)
	@SuppressWarnings("unchecked")
	public void test_findAll() throws Exception {
		//when
		final List<?> allFound = this.daoService().findAll();
		//then
		this.referenceAsserter().assertReferences(this.fetchReferences(), (List<Object>) allFound);
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Find)
	@SuppressWarnings({"unchecked", "serial", "rawtypes"})
	public void test_findById() throws Exception {
		//given
		final List<EntityReference> references = this.fetchReferences();
		final ID id = (ID) references.get(0).getReferenceValue("id");
		//when
		final Entity found = this.daoService().findById(id);
		//then
		this.referenceAsserter().assertReferences(references, new ArrayList() {
			{
				this.add(found);
			}
		});
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.UniqueConstraintsOnTable)
	@SuppressWarnings("unchecked")
	public void test_UniqueConstraintsOnTable() {
		for (final EntityReference reference : this.fetchReferences()) {
			boolean exceptionThrown = false;
			try {
				this.daoService().save((Entity) reference.getEntity());
			} catch (final DataIntegrityViolationException e) {
				exceptionThrown = true;
			}
			finally {
				if (!exceptionThrown) {
					final UniqueConstraintOnTableAssertion constraintAssertion = (UniqueConstraintOnTableAssertion) ((ConstraintReference) reference).getConstraintAssertion();
					this.constraintAsserter().addFailure(new UniqueConstraintOnTableAssertionFailure(reference.getEntity().getClass(), constraintAssertion));
				}
			}
		}
		this.constraintAsserter().assertConstraints();
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.UniqueConstraintsOnColumn)
	@SuppressWarnings("unchecked")
	public void test_UniqueConstraintsOnColumn() {
		for (final EntityReference reference : this.fetchReferences()) {
			boolean exceptionThrown = false;
			try {
				this.daoService().save((Entity) reference.getEntity());
			} catch (final DataIntegrityViolationException e) {
				exceptionThrown = true;
			}
			finally {
				if (!exceptionThrown) {
					final UniqueConstraintOnColumnAssertion constraintAssertion = (UniqueConstraintOnColumnAssertion) ((ConstraintReference) reference).getConstraintAssertion();
					this.constraintAsserter().addFailure(new UniqueConstraintOnColumnAssertionFailure(constraintAssertion, reference.getEntity().getClass()));
				}
			}
		}
		this.constraintAsserter().assertConstraints();
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.NullableConstraint)
	@SuppressWarnings("unchecked")
	public void test_NullableConstraints() {
		for (final EntityReference reference : this.fetchReferences()) {
			boolean exceptionThrown = false;
			try {
				this.daoService().save((Entity) reference.getEntity());
			} catch (final DataIntegrityViolationException e) {
				exceptionThrown = true;
			}
			finally {
				if (!exceptionThrown) {
					final NullableConstraintAssertion constraintAssertion = (NullableConstraintAssertion) ((ConstraintReference) reference).getConstraintAssertion();
					this.constraintAsserter().addFailure(new NullableConstraintAssertionFailure(constraintAssertion, reference.getEntity().getClass()));
				}
			}
		}
		this.constraintAsserter().assertConstraints();
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.NullableConstraint)
	@SuppressWarnings("unchecked")
	public void test_LengthConstraints() {
		for (final EntityReference reference : this.fetchReferences()) {
			boolean exceptionThrown = false;
			try {
				this.daoService().save((Entity) reference.getEntity());
			} catch (final DataIntegrityViolationException e) {
				exceptionThrown = true;
			}
			finally {
				if (!exceptionThrown) {
					final LengthConstraintAssertion constraintAssertion = (LengthConstraintAssertion) ((ConstraintReference) reference).getConstraintAssertion();
					this.constraintAsserter().addFailure(new LengthConstraintAssertionFailure(constraintAssertion, reference.getEntity().getClass()));
				}
			}
		}
		this.constraintAsserter().assertConstraints();
	}

	protected ConstraintAsserter constraintAsserter() {
		return TestPreparationContext.instance().constraintAsserter();
	}

	protected EntityUpdateProvider entityUpdateProvider() {
		return TestPreparationContext.instance().entityUpdateProvider();
	}

	protected EntityReferenceAsserter referenceAsserter() {
		return TestPreparationContext.instance().referenceAsserter();
	}

	protected List<EntityReference> fetchReferences() {
		return TestPreparationContext.instance().testDataHolder().getReferences(TestPhase.Running);
	}

	protected List<EntityReference> fetchReferences(final TestPhase testPhase) {
		return TestPreparationContext.instance().testDataHolder().getReferences(testPhase);
	}
}