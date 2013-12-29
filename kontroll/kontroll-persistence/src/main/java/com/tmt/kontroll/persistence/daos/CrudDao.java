package com.tmt.kontroll.persistence.daos;

import java.util.List;

/**
 * Interface defining common dao services with the standard methods to create, read, update, and delete entities.
 *
 * @param <Entity> the entity to work on.
 * @param <ID> the primary key.
 * @author Serg Derbst
 */
public interface CrudDao<Entity, ID> {

	List<Entity> findAll();

	Entity save(Entity entity);

	Entity findById(ID id);

	boolean exists(ID id);

	long count();

	void delete(ID id);

	List<Entity> saveAll(final List<Entity> entities);
}
