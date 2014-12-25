package com.tmt.kontroll.content.persistence.services;

import java.util.List;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.persistence.daos.CrudDao;

public interface ScopeDaoService extends CrudDao<Scope, Integer> {

	List<Scope> findAllByName(final String name);

	Scope findByNameAndRequestContextPath(final String name, final String requestContextPath);
}
