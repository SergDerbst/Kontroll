package com.tmt.kontroll.content.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.RequestContext;
import com.tmt.kontroll.content.persistence.repositories.RequestContextRepository;
import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;

@Service
public class RequestContextDaoService extends BaseCrudDaoService<RequestContextRepository, RequestContext> {

	@Autowired
	RequestContextRepository repository;

	@Override
	public RequestContextRepository getRepository() {
		return this.repository;
	}
}
