package com.tmt.kontroll.content.persistence.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentConditionRepository;
import com.tmt.kontroll.content.persistence.services.ScopedContentConditionDaoService;
import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;

@Transactional
@Service
public class ScopedContentConditionDaoServiceImpl extends BaseCrudDaoService<ScopedContentConditionRepository, ScopedContentCondition> implements ScopedContentConditionDaoService {

	@Autowired
	private ScopedContentConditionRepository	repository;

	@Override
	public List<ScopedContentCondition> findConditionsByScopeName(final String scopeName) {
		return null;
	}

	@Override
	public ScopedContentConditionRepository getRepository() {
		return this.repository;
	}
}
