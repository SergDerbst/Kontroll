package com.tmt.kontroll.content.persistence.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentConditionAttributeRepository;
import com.tmt.kontroll.content.persistence.services.ScopedContentConditionAttributeDaoService;
import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;

@Transactional
@Service
public class ScopedContentConditionAttributeDaoServiceImpl extends BaseCrudDaoService<ScopedContentConditionAttributeRepository, ScopedContentConditionAttribute> implements ScopedContentConditionAttributeDaoService {

	@Autowired
	private ScopedContentConditionAttributeRepository	repository;

	@Override
	public ScopedContentConditionAttributeRepository getRepository() {
		return this.repository;
	}
}
