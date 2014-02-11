package com.tmt.kontroll.content.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tmt.kontroll.content.config.ContentTestConfig;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentConditionRepository;
import com.tmt.kontroll.test.persistence.PersistenceBaseEntityDaoServiceTest;

@ContextConfiguration(classes = {ContentTestConfig.class})
public class ScopedContentConditionDaoServiceTest extends PersistenceBaseEntityDaoServiceTest<ScopedContentCondition, ScopedContentConditionRepository, ScopedContentConditionDaoService> {

	@Autowired
	private ScopedContentConditionDaoService service;

	@Override
	protected ScopedContentConditionDaoService daoService() {
		return this.service;
	}
}
