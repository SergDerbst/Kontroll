package com.tmt.kontroll.content.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tmt.kontroll.content.config.ContentTestConfig;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.repositories.ScopeRepository;
import com.tmt.kontroll.content.persistence.services.impl.ScopeDaoServiceImpl;
import com.tmt.kontroll.test.persistence.PersistenceBaseEntityDaoServiceTest;

@ContextConfiguration(classes = {ContentTestConfig.class})
public class ScopeDaoServiceTest extends PersistenceBaseEntityDaoServiceTest<Scope, ScopeRepository, ScopeDaoServiceImpl> {

	@Autowired
	ScopeDaoServiceImpl	service;

	@Override
	protected ScopeDaoServiceImpl daoService() {
		return this.service;
	}
}
