package com.tmt.kontroll.content.persistence.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.repositories.ScopeRepository;
import com.tmt.kontroll.content.persistence.services.ScopeDaoService;
import com.tmt.kontroll.content.persistence.specifications.ScopeQuerySpecifications;
import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;

@Service
public class ScopeDaoServiceImpl extends BaseCrudDaoService<ScopeRepository, Scope> implements ScopeDaoService {

	@Autowired
	ScopeRepository	repository;

	@Override
	public Scope findByName(final String name) {
		return this.repository.findByName(name);
	}

	@Override
	public Scope findByNameAndRequestContextPath(final String name, final String requestContextPath) {
		return this.repository.findOne(ScopeQuerySpecifications.byNameAndRequestContextPath(name, requestContextPath));
	}

	@Override
	public ScopeRepository getRepository() {
		return this.repository;
	}
}
