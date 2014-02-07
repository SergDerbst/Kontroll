package com.tmt.kontroll.content.persistence.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tmt.kontroll.content.config.ContentTestConfig;
import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.content.persistence.repositories.CaptionRepository;
import com.tmt.kontroll.test.persistence.PersistenceBaseEntityDaoServiceTest;

@ContextConfiguration(classes = {ContentTestConfig.class})
public class CaptionDaoServiceTest extends PersistenceBaseEntityDaoServiceTest<Caption, CaptionRepository, CaptionDaoService>{

	@Autowired
	CaptionDaoService service;

	@Override
	protected CaptionDaoService daoService() {
		return this.service;
	}
}