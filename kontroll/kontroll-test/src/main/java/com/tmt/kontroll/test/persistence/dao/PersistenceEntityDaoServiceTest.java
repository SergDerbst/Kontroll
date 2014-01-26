package com.tmt.kontroll.test.persistence.dao;

import java.io.Serializable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.tmt.kontroll.persistence.daos.CrudDao;
import com.tmt.kontroll.test.persistence.run.KontrollDbUnitTestExecutionListener;
import com.tmt.kontroll.test.persistence.run.dataset.KontrollDataSetLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	KontrollDbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = KontrollDataSetLoader.class)
public abstract class PersistenceEntityDaoServiceTest<ENTITY extends Object, ID extends Serializable, REPO extends JpaRepository<ENTITY, ID>, S extends CrudDao<ENTITY, ID>> {

	protected abstract S getDaoService();

	protected abstract ID getEntityId();

	@Test
	@DatabaseSetup("com.tmt.kontroll.content.persistence.entities.Caption")
	public void test_findById() {
		this.getDaoService().findById(this.getEntityId());
	}
}