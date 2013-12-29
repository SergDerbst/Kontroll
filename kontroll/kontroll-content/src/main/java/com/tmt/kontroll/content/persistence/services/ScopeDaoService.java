package com.tmt.kontroll.content.persistence.services;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.persistence.daos.CrudDao;


public interface ScopeDaoService extends CrudDao<Scope, Integer> {

	Scope findByName(String name);

}
