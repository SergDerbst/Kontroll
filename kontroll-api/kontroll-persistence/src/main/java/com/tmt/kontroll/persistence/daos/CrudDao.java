package com.tmt.kontroll.persistence.daos;

import java.util.List;

/**
 * Interface defining common dao services with the standard methods to create, read, update, and delete entities.
 *
 * @param <Entity> the entity to work on.
 * @param <ID> the primary key.
 * @author Sergio Weigel
 */
public interface CrudDao<Entity, ID> {

	long count();

	Entity create(Entity entity);

	List<Entity> createAll(final List<Entity> entities);

	void delete(ID id);

	boolean exists(ID id);

	List<Entity> readAll();

	Entity readById(ID id);

	Entity update(final Entity entity);
}
