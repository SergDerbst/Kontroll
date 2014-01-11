package com.tmt.kontroll.content.persistence.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentRepository;
import com.tmt.kontroll.persistence.daos.AbstractCrudDao;

@Service
public class ScopedContentDaoService extends AbstractCrudDao<ScopedContentRepository, ScopedContent, Integer> {

	@Autowired
	private ScopedContentRepository	repository;

	@Override
	public ScopedContentRepository getRepository() {
		return this.repository;
	}

	public List<ScopedContent> findByScope(final Scope scope) {
		return this.repository.findByScope(scope);
	}
}
