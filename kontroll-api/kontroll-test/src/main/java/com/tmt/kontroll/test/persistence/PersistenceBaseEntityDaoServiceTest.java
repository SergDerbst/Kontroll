package com.tmt.kontroll.test.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tmt.kontroll.persistence.BaseEntity;
import com.tmt.kontroll.persistence.daos.CrudDao;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

public abstract class PersistenceBaseEntityDaoServiceTest<ENTITY extends BaseEntity, REPO extends JpaRepository<ENTITY, Integer>, S extends CrudDao<ENTITY, Integer>> extends PersistenceEntityDaoServiceTest<ENTITY, Integer, REPO, S> {

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Create)
	@Override
	@SuppressWarnings({"unchecked"})
	public void testThatCreateWorks() throws Exception {
		//given
		final ENTITY entity = (ENTITY) super.fetchPrimaryReferences().get(0).entity();
		//when
		final ENTITY created = this.daoService().create(entity);
		//then
		assertNotNull(created);
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Create, numberOfEntities = 2)
	@Override
	@SuppressWarnings({	"unchecked",
			"rawtypes",
			"serial"})
	public void testThatCreateAllWorks() throws Exception {
		//given
		final List<EntityReference> references = super.fetchPrimaryReferences();
		//when
		final List created = this.daoService().createAll(new ArrayList() {
			{
				this.add(references.get(0).entity());
				this.add(references.get(1).entity());
			}
		});
		//then
		assertNotNull(created);
		assertFalse(created.isEmpty());
	}

	@Test
	@PersistenceTestConfig(testStrategy = TestStrategy.Update)
	@Override
	@SuppressWarnings({"unchecked"})
	public void testThatUpdateWorks() throws Exception {
		//given
		final ENTITY entity = (ENTITY) super.fetchPrimaryReferences().get(0).entity();
		//when
		final ENTITY updated = this.daoService().update(entity);
		//then
		assertNotNull(updated);
	}
}
