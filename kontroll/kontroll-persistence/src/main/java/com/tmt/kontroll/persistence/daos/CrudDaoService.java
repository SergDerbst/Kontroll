package com.tmt.kontroll.persistence.daos;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tmt.kontroll.persistence.exceptions.EntityNotFoundInDatabaseException;

/**
 * Abstract base implementation of {@link CrudDao}.
 *
 * @param <Entity> the entity to work on.
 * @param <ID> the primary key.
 * @param <Repo> the repository for the entity.
 * @author Serg Derbst
 */
@Transactional(rollbackFor = EntityNotFoundInDatabaseException.class)
public abstract class CrudDaoService<Repo extends JpaRepository<Entity, ID>, Entity, ID extends Serializable> implements CrudDao<Entity, ID> {

	public abstract Repo getRepository();

	@Override
	public abstract Entity update(final Entity entity) throws EntityNotFoundInDatabaseException;

	@Override
	public Entity readById(final ID id) {
		return this.getRepository().findOne(id);
	}

	@Override
	public List<Entity> readAll() {
		return this.getRepository().findAll();
	}

	@Override
	public Entity create(final Entity entity) {
		return this.getRepository().save(entity);
	}

	@Override
	public List<Entity> createAll(final List<Entity> entities) {
		return this.getRepository().save(entities);
	}

	@Override
	public void delete(final ID id) {
		this.getRepository().delete(id);
	}

	@Override
	public boolean exists(final ID id) {
		return this.getRepository().exists(id);
	}

	@Override
	public long count() {
		return this.getRepository().count();
	}
}
