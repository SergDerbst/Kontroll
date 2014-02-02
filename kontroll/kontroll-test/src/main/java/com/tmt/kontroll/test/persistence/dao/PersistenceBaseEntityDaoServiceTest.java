package com.tmt.kontroll.test.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tmt.kontroll.persistence.daos.CrudDao;
import com.tmt.kontroll.persistence.entities.BaseEntity;
import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.annotations.value.instantiation.IntegerValueInstantiation;
import com.tmt.kontroll.test.persistence.run.annotations.value.responsibility.ValueHandlingResponsibility;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

public abstract class PersistenceBaseEntityDaoServiceTest<ENTITY extends BaseEntity, REPO extends JpaRepository<ENTITY, Integer>, S extends CrudDao<ENTITY, Integer>> extends PersistenceEntityDaoServiceTest<ENTITY, Integer, REPO, S> {

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Save)
	@ValueHandlingResponsibility(fieldName = "id", types = {Integer.class})
	@IntegerValueInstantiation(1)
	@Override
	@SuppressWarnings({"unchecked", "rawtypes", "serial"})
	public void test_save() {
		//given
		final ENTITY entity = (ENTITY) referenceHolder.getReferences().get(0).getReference();
		//when
		final ENTITY saved = this.getDaoService().save(entity);
		//then
		asserter.assertReferences(referenceHolder.getReferences(), new ArrayList() {
			{
				this.add(saved);
			}
		});
	}

	//doesn't work, because value handler needs to be set up per test method
	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Save)
	@ValueHandlingResponsibility(fieldName = "id", types = {Integer.class})
	@IntegerValueInstantiation(1)
	//	@Override
	@SuppressWarnings({"unchecked", "rawtypes", "serial"})
	public void test_saveAll() {
		//when
		final List saved = this.getDaoService().saveAll(new ArrayList(){{
			this.add(referenceHolder.getReferences().get(0).getReference());
			this.add(referenceHolder.getReferences().get(1).getReference());
		}});
		//then
		asserter.assertReferences(referenceHolder.getReferences(), saved);
	}
}
