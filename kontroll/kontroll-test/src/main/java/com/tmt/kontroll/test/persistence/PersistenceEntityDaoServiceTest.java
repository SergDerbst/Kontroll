package com.tmt.kontroll.test.persistence;

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
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.reference.Reference;
import com.tmt.kontroll.test.persistence.run.data.reference.ReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class, KontrollDbUnitTestExecutionListener.class})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public abstract class PersistenceEntityDaoServiceTest<ENTITY extends Object, ID extends Serializable, REPO extends JpaRepository<ENTITY, ID>, S extends CrudDao<ENTITY, ID>> {

	protected abstract S getDaoService();

	public abstract void test_save() throws Exception;

	public abstract void test_saveAll() throws Exception;

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Count, numberOfEntities = 2)
	public void test_count() {
		//when
		assertEquals(new Long(2), (Long) this.getDaoService().count());
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Delete)
	@SuppressWarnings("unchecked")
	public void test_delete() throws Exception {
		//given
		final ID id = (ID) this.fetchReferences().get(0).getReferenceValue("id");
		//when
		this.getDaoService().delete(id);
		//then
		assertEquals(new Long(0), (Long) this.getDaoService().count());
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Exists)
	@SuppressWarnings("unchecked")
	public void test_exists() throws Exception {
		//given
		final ID id = (ID) this.fetchReferences().get(0).getReferenceValue("id");
		//when
		final boolean exists = this.getDaoService().exists(id);
		//then
		assertTrue(exists);
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Find, numberOfEntities = 2)
	@SuppressWarnings("unchecked")
	public void test_findAll() throws Exception {
		//when
		final List<?> allFound = this.getDaoService().findAll();
		//then
		this.fetchReferenceAsserter().assertReferences(this.fetchReferences(), (List<Object>) allFound);
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Find)
	@SuppressWarnings({"unchecked", "serial", "rawtypes"})
	public void test_findById() throws Exception {
		//given
		final List<Reference> references = this.fetchReferences();
		final ID id = (ID) references.get(0).getReferenceValue("id");
		//when
		final ENTITY found = this.getDaoService().findById(id);
		//then
		this.fetchReferenceAsserter().assertReferences(references, new ArrayList() {
			{
				this.add(found);
			}
		});
	}

	protected ReferenceAsserter fetchReferenceAsserter() {
		return TestDataHolder.instance().getReferenceAsserter();
	}

	protected List<Reference> fetchReferences() {
		return TestDataHolder.instance().getReferences();
	}
}