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
public abstract class AbstractCrudDao<Repo extends JpaRepository<Entity, ID>, Entity, ID extends Serializable> implements CrudDao<Entity, ID> {

	public abstract Repo getRepository();

	@Override
	public List<Entity> findAll() {
		return this.getRepository().findAll();
	}

	@Override
	public Entity save(final Entity entity) {
		return this.getRepository().save(entity);
	}

	@Override
	public Entity findById(final ID id) {
		return this.getRepository().findOne(id);
	}

	@Override
	public boolean exists(final ID id) {
		return this.getRepository().exists(id);
	}

	@Override
	public long count() {
		return this.getRepository().count();
	}

	@Override
	public void delete(final ID id) {
		this.getRepository().delete(id);
	}

	@Override
	public List<Entity> saveAll(final List<Entity> entities) {
		return this.getRepository().save(entities);
	}
}
