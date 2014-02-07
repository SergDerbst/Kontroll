package com.tmt.kontroll.persistence.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmt.kontroll.persistence.entities.BaseEntity;
import com.tmt.kontroll.persistence.exceptions.EntityNotFoundInDatabaseException;
import com.tmt.kontroll.persistence.utils.EntityHelper;

public abstract class BaseCrudDaoService<Repo extends JpaRepository<Entity, Integer>, Entity extends BaseEntity> extends CrudDaoService<Repo, Entity, Integer> {

	@Override
	public Entity update(final Entity entity) throws EntityNotFoundInDatabaseException {
		final Entity found = this.getRepository().findOne(entity.getId());
		if (found == null) {
			throw EntityNotFoundInDatabaseException.prepare(entity);
		}
		return EntityHelper.updateEntity(found, entity);
	}
}
