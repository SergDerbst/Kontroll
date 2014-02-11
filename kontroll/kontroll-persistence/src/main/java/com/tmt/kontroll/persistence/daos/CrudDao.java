package com.tmt.kontroll.persistence.daos;

import java.util.List;

import com.tmt.kontroll.persistence.exceptions.EntityNotFoundInDatabaseException;

/**
 * Interface defining common dao services with the standard methods to create, read, update, and delete entities.
 *
 * @param <Entity> the entity to work on.
 * @param <ID> the primary key.
 * @author Serg Derbst
 */
public interface CrudDao<Entity, ID> {

	Entity readById(ID id);

	List<Entity> readAll();

	Entity create(Entity entity);

	List<Entity> createAll(final List<Entity> entities);

	Entity update(final Entity entity) throws EntityNotFoundInDatabaseException;

	void delete(ID id);

	boolean exists(ID id);

	long count();

}
