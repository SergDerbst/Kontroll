package com.tmt.kontroll.content.persistence.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentConditionRepository;
import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;

@Service
public class ScopedContentConditionDaoService extends BaseCrudDaoService<ScopedContentConditionRepository, ScopedContentCondition> {

	@Autowired
	private ScopedContentConditionRepository	repository;

	@Override
	public ScopedContentConditionRepository getRepository() {
		return this.repository;
	}

	public List<ScopedContentCondition> findConditionsByScopeName(final String scopeName) {
		return null;
	}
}
