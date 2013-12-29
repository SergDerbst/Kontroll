package com.tmt.kontroll.content.persistence.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentConditionRepository;
import com.tmt.kontroll.content.persistence.services.ScopedContentConditionDaoService;
import com.tmt.kontroll.persistence.daos.AbstractCrudDao;

@Service
public class ScopedContentConditionDaoServiceImpl extends AbstractCrudDao<ScopedContentConditionRepository, ScopedContentCondition, Integer> implements ScopedContentConditionDaoService {

	@Autowired
	private ScopedContentConditionRepository	repository;

	@Override
	public ScopedContentConditionRepository getRepository() {
		return this.repository;
	}

	@Override
	public List<ScopedContentCondition> findConditionsByScopeName(String scopeName) {
		// TODO Auto-generated method stub
		return null;
	}
}
