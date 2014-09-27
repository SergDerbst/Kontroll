package com.tmt.kontroll.content.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tmt.kontroll.content.config.ContentTestConfig;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentItemRepository;
import com.tmt.kontroll.content.persistence.services.impl.ScopedContentItemDaoServiceImpl;
import com.tmt.kontroll.test.persistence.PersistenceBaseEntityDaoServiceTest;

@ContextConfiguration(classes = {ContentTestConfig.class})
public class ScopedContentItemDaoServiceTest extends PersistenceBaseEntityDaoServiceTest<ScopedContentItem, ScopedContentItemRepository, ScopedContentItemDaoServiceImpl> {

	@Autowired
	private ScopedContentItemDaoServiceImpl service;

	@Override
	protected ScopedContentItemDaoServiceImpl daoService() {
		return this.service;
	}
}
