package com.tmt.kontroll.test.persistence;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tmt.kontroll.persistence.daos.CrudDao;
import com.tmt.kontroll.persistence.entities.BaseEntity;
import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;

public abstract class PersistenceBaseEntityDaoServiceTest<ENTITY extends BaseEntity, REPO extends JpaRepository<ENTITY, Integer>, S extends CrudDao<ENTITY, Integer>> extends PersistenceEntityDaoServiceTest<ENTITY, Integer, REPO, S> {

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Save, ignoreEntityId = true)
	@Override
	@SuppressWarnings({"unchecked", "rawtypes", "serial"})
	public void test_save() throws Exception {
		//given
		final ENTITY entity = (ENTITY) super.fetchReferences().get(0).getEntity();
		//when
		final ENTITY saved = this.daoService().save(entity);
		//then
		super.referenceAsserter().assertReferences(super.fetchReferences(), new ArrayList() {
			{
				this.add(saved);
			}
		});
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Save, numberOfEntities = 2, ignoreEntityId = true)
	@Override
	@SuppressWarnings({"unchecked", "rawtypes", "serial"})
	public void test_saveAll() throws Exception {
		//given
		final List<EntityReference> references = super.fetchReferences();
		//when
		final List saved = this.daoService().saveAll(new ArrayList(){{
			this.add(references.get(0).getEntity());
			this.add(references.get(1).getEntity());
		}});
		//then
		super.referenceAsserter().assertReferences(references, saved);
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Update)
	@Override
	@SuppressWarnings({"unchecked", "rawtypes", "serial"})
	public void test_update() throws Exception {
		//given
		final ENTITY entity = (ENTITY) super.fetchReferences().get(0).getEntity();
		//when
		final ENTITY updated = this.daoService().update(entity);
		//then
		super.referenceAsserter().assertReferences(super.fetchReferences(), new ArrayList() {
			{
				this.add(updated);
			}
		});
	}
}
