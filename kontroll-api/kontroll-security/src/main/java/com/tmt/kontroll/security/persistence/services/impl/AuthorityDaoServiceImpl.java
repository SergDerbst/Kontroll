package com.tmt.kontroll.security.persistence.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;
import com.tmt.kontroll.security.persistence.entities.Authority;
import com.tmt.kontroll.security.persistence.repositories.AuthorityRepository;
import com.tmt.kontroll.security.persistence.services.AuthorityDaoService;

@Service
public class AuthorityDaoServiceImpl extends BaseCrudDaoService<AuthorityRepository, Authority> implements AuthorityDaoService {

	@Autowired
	AuthorityRepository	repository;

	@Override
	public AuthorityRepository getRepository() {
		return this.repository;
	}

	@Override
	public Authority findByName(final String name) {
		return this.repository.findByName(name);
	}
}
