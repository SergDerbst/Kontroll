package com.tmt.kontroll.persistence.daos;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Abstract base implementation of {@link CrudDao}.
 *
 * @param <Entity> the entity to work on.
 * @param <ID> the primary key.
 * @param <Repo> the repository for the entity.
 * @author Serg Derbst
 */
public abstract class AbstractCrudDao<Repo extends JpaRepository<Entity, ID>, Entity, ID extends Serializable> {

	public abstract Repo getRepository();

	public List<Entity> findAll() {
		return getRepository().findAll();
	}

	public Entity save(final Entity entity) {
		return getRepository().save(entity);
	}

	public Entity findById(final ID id) {
		return getRepository().findOne(id);
	}

	public boolean exists(final ID id) {
		return getRepository().exists(id);
	}

	public long count() {
		return getRepository().count();
	}

	public void delete(final ID id) {
		getRepository().delete(id);
	}

	public List<Entity> saveAll(final List<Entity> entities) {
		return getRepository().save(entities);
	}
}
