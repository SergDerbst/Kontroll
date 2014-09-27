package com.tmt.kontroll.content.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tmt.kontroll.content.config.ContentTestConfig;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentConditionAttributeRepository;
import com.tmt.kontroll.content.persistence.services.impl.ScopedContentConditionAttributeDaoServiceImpl;
import com.tmt.kontroll.test.persistence.PersistenceBaseEntityDaoServiceTest;

@ContextConfiguration(classes = {ContentTestConfig.class})
public class ScopedContentConditionAttributeDaoServiceTest extends PersistenceBaseEntityDaoServiceTest<ScopedContentConditionAttribute, ScopedContentConditionAttributeRepository, ScopedContentConditionAttributeDaoServiceImpl> {

	@Autowired
	private ScopedContentConditionAttributeDaoServiceImpl service;

	@Override
	protected ScopedContentConditionAttributeDaoServiceImpl daoService() {
		return this.service;
	}
}
