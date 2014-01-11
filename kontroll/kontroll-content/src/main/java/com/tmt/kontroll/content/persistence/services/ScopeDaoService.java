package com.tmt.kontroll.content.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.repositories.ScopeRepository;
import com.tmt.kontroll.content.persistence.specifications.ScopeQuerySpecifications;
import com.tmt.kontroll.persistence.daos.AbstractCrudDao;

@Service
public class ScopeDaoService extends AbstractCrudDao<ScopeRepository, Scope, Integer> {

	@Autowired
	ScopeRepository	repository;

	@Override
	public ScopeRepository getRepository() {
		return this.repository;
	}

	public Scope findByName(final String name) {
		return this.repository.findByName(name);
	}

	public Scope findByNameAndRequestContextPath(final String name, final String requestContextPath) {
		return this.repository.findOne(ScopeQuerySpecifications.byNameAndRequestContextPath(name, requestContextPath));
	}
}
