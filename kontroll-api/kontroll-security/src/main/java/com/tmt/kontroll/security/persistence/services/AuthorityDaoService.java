package com.tmt.kontroll.security.persistence.services;

import com.tmt.kontroll.persistence.daos.CrudDao;
import com.tmt.kontroll.security.persistence.entities.Authority;

public interface AuthorityDaoService extends CrudDao<Authority, Integer> {

	Authority findByName(final String name);
}
