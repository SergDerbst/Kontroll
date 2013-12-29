package com.tmt.kontroll.content.persistence.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.repositories.ScopeRepository;
import com.tmt.kontroll.content.persistence.services.ScopeDaoService;
import com.tmt.kontroll.persistence.daos.AbstractCrudDao;

@Service
public class ScopeDaoServiceImpl extends AbstractCrudDao<ScopeRepository, Scope, Integer> implements ScopeDaoService {

	@Autowired
	private ScopeRepository	repository;

	@Override
	public ScopeRepository getRepository() {
		return this.repository;
	}

	@Override
	public Scope findByName(final String name) {
		return this.repository.findByName(name);
	}
}
