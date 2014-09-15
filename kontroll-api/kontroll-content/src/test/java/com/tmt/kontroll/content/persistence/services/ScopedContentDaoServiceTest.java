package com.tmt.kontroll.content.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tmt.kontroll.content.config.ContentTestConfig;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentRepository;
import com.tmt.kontroll.test.persistence.PersistenceBaseEntityDaoServiceTest;

@ContextConfiguration(classes = {ContentTestConfig.class})
public class ScopedContentDaoServiceTest extends PersistenceBaseEntityDaoServiceTest<ScopedContent, ScopedContentRepository, ScopedContentDaoService> {

	@Autowired
	private ScopedContentDaoService service;

	@Override
	protected ScopedContentDaoService daoService() {
		return this.service;
	}
}
