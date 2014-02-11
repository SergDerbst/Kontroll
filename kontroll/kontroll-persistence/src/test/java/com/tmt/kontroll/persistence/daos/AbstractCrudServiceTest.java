package com.tmt.kontroll.persistence.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Abstract test class providing all test methods needed for testing the CRUD service methods.
 *
 * @param <ENTITY> - the entity this service is for.
 * @param <REPO> - the repository of this entity.
 * @param <ID> - the type of the entity's id.
 * @param <S> - the dao service type to test.
 *
 * @author Serg Derbst
 */
public abstract class AbstractCrudServiceTest<ENTITY extends Object, REPO extends JpaRepository<ENTITY, ID>, ID extends Serializable, S extends CrudDao<ENTITY, ID>> {

	/**
	 * The number of entities of this type that are not being used for actual tests
	 * in this instance, but that have to be created for tests of other services,
	 * e.g. due to foreign key constraints. Defaults to <code>0</code>.
	 */
	private final int numberOfExtraInitialEntities;

	protected AbstractCrudServiceTest() {
		this.numberOfExtraInitialEntities = 0;
	}

	protected AbstractCrudServiceTest(final int numberOfExtraInitialEntities) {
		this.numberOfExtraInitialEntities = numberOfExtraInitialEntities;
	}

	protected abstract S getDaoService();

	protected abstract ID getDeleteId();

	protected abstract ID getExistsId();

	protected abstract ID getFindId();

	protected abstract Long getNumberOfInitialEntities();

	protected abstract ENTITY getEntityToSave();

	protected abstract List<ENTITY> getEntitiesToSave();

	@Ignore
	@Test
	public void test_count() {
		assertEquals(new Long(this.getNumberOfInitialEntities() + this.numberOfExtraInitialEntities), new Long(this.getDaoService().count()));
	}

	@Ignore
	@Test
	public void test_delete() {
		this.getDaoService().delete(this.getDeleteId());
		assertEquals(this.getNumberOfInitialEntities() + this.numberOfExtraInitialEntities - 1, this.getDaoService().count());
	}

	@Ignore
	@Test
	public void test_exists() {
		assertTrue(this.getDaoService().exists(this.getExistsId()));
	}

	@Ignore
	@Test
	public void test_findAll() {
		assertEquals(new Long(this.getNumberOfInitialEntities() + this.numberOfExtraInitialEntities), new Long(this.getDaoService().readAll().size()));
	}

	@Ignore
	@Test
	public void test_findById() {
		final ENTITY findById = this.getDaoService().readById(this.getFindId());
		assertNotNull(findById);
	}

	@Ignore
	@Test
	public void test_save() {
		this.getDaoService().count();
		final ENTITY entityToSave = this.getEntityToSave();
		final ENTITY e = this.getDaoService().create(entityToSave);
		assertNotNull(e);
		assertEquals(entityToSave.getClass(), e.getClass());
		assertEquals(new Long(this.getNumberOfInitialEntities() + this.numberOfExtraInitialEntities + 1), new Long(this.getDaoService().count()));
	}

	@Ignore
	@Test
	public void test_saveAll() {
		final List<ENTITY> entitiesToSave = this.getEntitiesToSave();
		final List<ENTITY> e = this.getDaoService().createAll(entitiesToSave);
		assertNotNull(e);
		assertFalse(e.isEmpty());
		assertEquals(entitiesToSave.get(0).getClass(), e.get(0).getClass());
		assertEquals(new Long(this.getNumberOfInitialEntities() + this.numberOfExtraInitialEntities + this.getEntitiesToSave().size()), new Long(this.getDaoService().count()));
	}
}
