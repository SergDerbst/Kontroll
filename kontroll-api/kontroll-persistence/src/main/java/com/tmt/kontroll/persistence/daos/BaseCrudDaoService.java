package com.tmt.kontroll.persistence.daos;

import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.updateEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmt.kontroll.persistence.entities.BaseEntity;
import com.tmt.kontroll.persistence.exceptions.EntityNotFoundInDatabaseException;

public abstract class BaseCrudDaoService<Repo extends JpaRepository<Entity, Integer>, Entity extends BaseEntity> extends CrudDaoService<Repo, Entity, Integer> {

	@Override
	public Entity update(final Entity entity) throws EntityNotFoundInDatabaseException {
		final Entity found = this.getRepository().findOne(entity.getId());
		if (found == null) {
			throw EntityNotFoundInDatabaseException.prepare(entity);
		}
		return updateEntity(found, entity);
	}
}
