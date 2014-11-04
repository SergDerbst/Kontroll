package com.tmt.kontroll.content.persistence.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentRepository;
import com.tmt.kontroll.content.persistence.services.ScopedContentDaoService;
import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;

@Transactional
@Service
public class ScopedContentDaoServiceImpl extends BaseCrudDaoService<ScopedContentRepository, ScopedContent> implements ScopedContentDaoService {

	@Autowired
	private ScopedContentRepository	repository;

	@Override
	public List<ScopedContent> findByScope(final Scope scope) {
		return this.repository.findByScope(scope);
	}

	@Override
	public ScopedContentRepository getRepository() {
		return this.repository;
	}
}
