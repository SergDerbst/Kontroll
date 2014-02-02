package com.tmt.kontroll.test.persistence.dao;

import static com.tmt.kontroll.test.persistence.run.assertion.PersistenceEntityReferenceHolder.getReferences;
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
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.tmt.kontroll.persistence.daos.CrudDao;
import com.tmt.kontroll.test.persistence.run.KontrollDbUnitTestExecutionListener;
import com.tmt.kontroll.test.persistence.run.annotations.DbSetup;
import com.tmt.kontroll.test.persistence.run.assertion.PersistenceEntityReferenceAsserter;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	KontrollDbUnitTestExecutionListener.class })
public abstract class PersistenceEntityDaoServiceTest<ENTITY extends Object, ID extends Serializable, REPO extends JpaRepository<ENTITY, ID>, S extends CrudDao<ENTITY, ID>> {

	private static final PersistenceEntityReferenceAsserter asserter = PersistenceEntityReferenceAsserter.instance();

	protected abstract S getDaoService();

	@Test
	@DbSetup(numberOfEntities = 2)
	public void test_count() {
		//when
		assertEquals(new Long(2), (Long) this.getDaoService().count());
	}

	@Test
	@DbSetup
	@SuppressWarnings("unchecked")
	public void test_delete() {
		//given
		final ID id = (ID) getReferences().get(0).getReferenceValue("id");
		//when
		this.getDaoService().delete(id);
		//then
		assertEquals(new Long(0), (Long) this.getDaoService().count());
	}

	@Test
	@DbSetup
	@SuppressWarnings("unchecked")
	public void test_exists() {
		//given
		final ID id = (ID) getReferences().get(0).getReferenceValue("id");
		//when
		final boolean exists = this.getDaoService().exists(id);
		//then
		assertTrue(exists);
	}

	@Test
	@DbSetup(numberOfEntities = 2)
	@SuppressWarnings("unchecked")
	public void test_findAll() {
		//when
		final List<ENTITY> allFound = this.getDaoService().findAll();
		//then
		asserter.assertReferences(getReferences(), (List<Object>) allFound);
	}

	@Test
	@DbSetup
	@SuppressWarnings({"unchecked", "serial", "rawtypes"})
	public void test_findById() {
		//given
		final ID id = (ID) getReferences().get(0).getReferenceValue("id");
		//when
		final ENTITY found = this.getDaoService().findById(id);
		//then
		asserter.assertReferences(getReferences(), new ArrayList() {{
			this.add(found);
		}});
	}
}