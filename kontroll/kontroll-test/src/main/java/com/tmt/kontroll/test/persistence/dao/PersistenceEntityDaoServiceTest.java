package com.tmt.kontroll.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.tmt.kontroll.persistence.daos.CrudDao;
import com.tmt.kontroll.test.persistence.run.KontrollDbUnitTestExecutionListener;
import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.reference.ReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.reference.ReferenceHolder;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class, KontrollDbUnitTestExecutionListener.class})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public abstract class PersistenceEntityDaoServiceTest<ENTITY extends Object, ID extends Serializable, REPO extends JpaRepository<ENTITY, ID>, S extends CrudDao<ENTITY, ID>> {

	protected static final ReferenceAsserter asserter = ReferenceAsserter.instance();
	protected static final ReferenceHolder referenceHolder = ReferenceHolder.instance();

	protected abstract S getDaoService();

	public abstract void test_save();

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Count, numberOfEntities = 2)
	public void test_count() {
		//when
		assertEquals(new Long(2), (Long) this.getDaoService().count());
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Delete)
	@SuppressWarnings("unchecked")
	public void test_delete() {
		//given
		final ID id = (ID) referenceHolder.getReferences().get(0).getReferenceValue("id");
		//when
		this.getDaoService().delete(id);
		//then
		assertEquals(new Long(0), (Long) this.getDaoService().count());
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Exists)
	@SuppressWarnings("unchecked")
	public void test_exists() {
		//given
		final ID id = (ID) referenceHolder.getReferences().get(0).getReferenceValue("id");
		//when
		final boolean exists = this.getDaoService().exists(id);
		//then
		assertTrue(exists);
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Find, numberOfEntities = 2)
	@SuppressWarnings("unchecked")
	public void test_findAll() {
		//when
		final List<?> allFound = this.getDaoService().findAll();
		//then
		asserter.assertReferences(referenceHolder.getReferences(), (List<Object>) allFound);
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Find)
	@SuppressWarnings({"unchecked", "serial", "rawtypes"})
	public void test_findById() {
		//given
		final ID id = (ID) referenceHolder.getReferences().get(0).getReferenceValue("id");
		//when
		final ENTITY found = this.getDaoService().findById(id);
		//then
		asserter.assertReferences(referenceHolder.getReferences(), new ArrayList() {
			{
				this.add(found);
			}
		});
	}
}