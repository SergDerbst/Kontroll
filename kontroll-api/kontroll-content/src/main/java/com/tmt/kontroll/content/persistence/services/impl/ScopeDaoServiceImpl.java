package com.tmt.kontroll.content.persistence.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysema.query.jpa.impl.JPAQuery;
import com.tmt.kontroll.content.persistence.entities.QScope;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.repositories.ScopeRepository;
import com.tmt.kontroll.content.persistence.services.ScopeDaoService;
import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;

@Service
public class ScopeDaoServiceImpl extends BaseCrudDaoService<ScopeRepository, Scope> implements ScopeDaoService {

	@PersistenceContext
	EntityManager		entityManager;

	@Autowired
	ScopeRepository	repository;

	@Override
	public List<Scope> findAllByName(final String name) {
		return this.repository.findAllByName(name);
	}

	@Override
	public Scope findByNameAndRequestPattern(final String name, final String requestContextPath) {
		final JPAQuery query = new JPAQuery(this.entityManager);
		final QScope scope = QScope.scope;
		return query.from(scope).where(scope.name.eq(name).and(scope.requestPattern.like(requestContextPath))).uniqueResult(scope);
	}

	@Override
	public ScopeRepository getRepository() {
		return this.repository;
	}
}
