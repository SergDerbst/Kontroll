package com.tmt.kontroll.content.persistence.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentRepository;
import com.tmt.kontroll.content.persistence.services.ScopedContentDaoService;
import com.tmt.kontroll.persistence.daos.AbstractCrudDao;

@Service
public class ScopedContentDaoServiceImpl extends AbstractCrudDao<ScopedContentRepository, ScopedContent, Integer> implements ScopedContentDaoService {

	@Autowired
	private ScopedContentRepository	repository;

	@Override
	public ScopedContentRepository getRepository() {
		return this.repository;
	}

	@Override
	public List<ScopedContent> findByScopeAndName(Scope scope,
																								String name) {
		return this.repository.findByScopeAndName(scope, name);
	}
}
