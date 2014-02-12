package com.tmt.kontroll.test.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
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
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class, KontrollDbUnitTestExecutionListener.class})
@TransactionConfiguration(defaultRollback = false)
public abstract class PersistenceEntityDaoServiceTest<Entity extends Object, ID extends Serializable, REPO extends JpaRepository<Entity, ID>, S extends CrudDao<Entity, ID>> {

	protected abstract S daoService();

	public abstract void testThatCreateWorks() throws Exception;

	public abstract void testThatCreateAllWorks() throws Exception;

	public abstract void testThatUpdateWorks() throws Exception;

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Count, numberOfEntities = 2)
	public void testThatCountWorks() {
		//when
		assertEquals(new Long(this.fetchPrimaryTypeReferences().size()), (Long) this.daoService().count());
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Delete)
	@SuppressWarnings("unchecked")
	public void testThatDeleteWorks() throws Exception {
		//given
		final ID id = (ID) this.fetchPrimaryReferences().get(0).getReferenceValue("id");
		//when
		this.daoService().delete(id);
		//then
		assertEquals(new Long(this.fetchPrimaryTypeReferences(TestPhase.Verification).size()), (Long) this.daoService().count());
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Exists)
	@SuppressWarnings("unchecked")
	public void testThatExistsWorks() throws Exception {
		//given
		final ID id = (ID) this.fetchPrimaryReferences().get(0).getReferenceValue("id");
		//when
		final boolean exists = this.daoService().exists(id);
		//then
		assertTrue(exists);
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Read, numberOfEntities = 2)
	public void testThatReadAllWorks() throws Exception {
		//when
		final List<?> allFound = this.daoService().readAll();
		//then
		assertNotNull(allFound);
		assertFalse(allFound.isEmpty());
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Read)
	@SuppressWarnings({"unchecked"})
	public void testThatReadWorks() throws Exception {
		//given
		final List<EntityReference> references = this.fetchPrimaryReferences();
		final ID id = (ID) references.get(0).getReferenceValue("id");
		//when
		final Entity found = this.daoService().readById(id);
		//then
		assertNotNull(found);
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.UniqueConstraintsOnTable)
	@SuppressWarnings("unchecked")
	public void testThatUniqueConstraintsOnTableViolationsCauseError() {
		for (final EntityReference reference : this.fetchPrimaryReferences()) {
			boolean exceptionThrown = false;
			try {
				this.daoService().create((Entity) reference.getEntity());
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
	public void testThatUniqueConstraintsOnColumnViolationsCauseError() {
		for (final EntityReference reference : this.fetchPrimaryReferences()) {
			boolean exceptionThrown = false;
			try {
				this.daoService().create((Entity) reference.getEntity());
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
	public void testThatNullableConstraintsViolationsCauseError() {
		for (final EntityReference reference : this.fetchPrimaryReferences()) {
			boolean exceptionThrown = false;
			try {
				this.daoService().create((Entity) reference.getEntity());
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
	public void testThatLengthConstraintsViolationsCauseError() {
		for (final EntityReference reference : this.fetchPrimaryReferences()) {
			boolean exceptionThrown = false;
			try {
				this.daoService().create((Entity) reference.getEntity());
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
		return PersistenceTestContext.instance().constraintAsserter();
	}

	protected EntityReferenceAsserter referenceAsserter() {
		return PersistenceTestContext.instance().entityReferenceAsserter();
	}

	protected Set<EntityReference> fetchReferences() {
		return PersistenceTestContext.instance().testDataHolder().fetchReferences(TestPhase.Running);
	}

	protected Set<EntityReference> fetchReferences(final TestPhase testPhase) {
		return PersistenceTestContext.instance().testDataHolder().fetchReferences(testPhase);
	}

	protected List<EntityReference> fetchPrimaryTypeReferences() {
		return PersistenceTestContext.instance().testDataHolder().fetchPrimaryTypeReferences(TestPhase.Running);
	}

	protected List<EntityReference> fetchPrimaryTypeReferences(final TestPhase testPhase) {
		return PersistenceTestContext.instance().testDataHolder().fetchPrimaryTypeReferences(testPhase);
	}

	protected List<EntityReference> fetchPrimaryReferences() {
		return PersistenceTestContext.instance().testDataHolder().fetchPrimaryReferences(TestPhase.Running);
	}

	protected List<EntityReference> fetchPrimaryReferences(final TestPhase testPhase) {
		return PersistenceTestContext.instance().testDataHolder().fetchPrimaryReferences(testPhase);
	}

	protected List<EntityReference> fetchNonPrimaryReferences() {
		return PersistenceTestContext.instance().testDataHolder().fetchNonPrimaryReferences(TestPhase.Running);
	}

	protected List<EntityReference> fetchNonPrimaryReferences(final TestPhase testPhase) {
		return PersistenceTestContext.instance().testDataHolder().fetchNonPrimaryReferences(testPhase);
	}
}