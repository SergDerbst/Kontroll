package com.tmt.kontroll.content.persistence.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tmt.kontroll.content.config.ContentTestConfig;
import com.tmt.kontroll.content.persistence.entities.RequestContext;
import com.tmt.kontroll.content.persistence.repositories.RequestContextRepository;
import com.tmt.kontroll.test.persistence.PersistenceBaseEntityDaoServiceTest;

@ContextConfiguration(classes = {ContentTestConfig.class})
public class RequestContextDaoServiceTest extends PersistenceBaseEntityDaoServiceTest<RequestContext, RequestContextRepository, RequestContextDaoService> {

	@Autowired
	private RequestContextDaoService service;

	@Override
	protected RequestContextDaoService daoService() {
		return this.service;
	}
}
