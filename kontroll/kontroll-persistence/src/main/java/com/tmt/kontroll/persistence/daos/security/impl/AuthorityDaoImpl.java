package com.tmt.kontroll.persistence.daos.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.persistence.daos.AbstractCrudDao;
import com.tmt.kontroll.persistence.daos.security.AuthorityDao;
import com.tmt.kontroll.persistence.entities.security.Authority;
import com.tmt.kontroll.persistence.repositories.security.AuthorityRepository;

@Service
public class AuthorityDaoImpl extends AbstractCrudDao<AuthorityRepository, Authority, Integer> implements AuthorityDao {

	@Autowired
	private AuthorityRepository	repository;

	@Override
	public AuthorityRepository getRepository() {
		return repository;
	}
}
