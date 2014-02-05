package com.tmt.kontroll.test.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tmt.kontroll.persistence.daos.CrudDao;
import com.tmt.kontroll.persistence.entities.BaseEntity;
import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.reference.Reference;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

public abstract class PersistenceBaseEntityDaoServiceTest<ENTITY extends BaseEntity, REPO extends JpaRepository<ENTITY, Integer>, S extends CrudDao<ENTITY, Integer>> extends PersistenceEntityDaoServiceTest<ENTITY, Integer, REPO, S> {

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Save, ignoreEntityId = true)
	@Override
	@SuppressWarnings({"unchecked", "rawtypes", "serial"})
	public void test_save() throws Exception {
		//given
		final ENTITY entity = (ENTITY) super.fetchReferences().get(0).getReference();
		//when
		final ENTITY saved = this.getDaoService().save(entity);
		//then
		super.fetchReferenceAsserter().assertReferences(super.fetchReferences(), new ArrayList() {
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
		final List<Reference> references = super.fetchReferences();
		//when
		final List saved = this.getDaoService().saveAll(new ArrayList(){{
			this.add(references.get(0).getReference());
			this.add(references.get(1).getReference());
		}});
		//then
		super.fetchReferenceAsserter().assertReferences(references, saved);
	}
}
