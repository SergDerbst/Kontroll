package com.tmt.kontroll.content.persistence.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tmt.kontroll.content.config.ContentTestConfig;
import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.content.persistence.repositories.CaptionRepository;
import com.tmt.kontroll.test.persistence.dao.PersistenceEntityDaoServiceTest;

//@Ignore
@ContextConfiguration(classes = {ContentTestConfig.class})
public class CaptionDaoServiceTest extends PersistenceEntityDaoServiceTest<Caption, Integer, CaptionRepository, CaptionDaoService>{

	@Autowired
	CaptionDaoService service;

	@Override
	protected CaptionDaoService getDaoService() {
		return this.service;
	}

	@Override
	protected Integer getEntityId() {
		return 0;
	}
}
