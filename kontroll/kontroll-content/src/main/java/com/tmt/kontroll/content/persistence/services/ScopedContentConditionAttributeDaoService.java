package com.tmt.kontroll.content.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentConditionAttributeRepository;
import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;

@Service
public class ScopedContentConditionAttributeDaoService extends BaseCrudDaoService<ScopedContentConditionAttributeRepository, ScopedContentConditionAttribute> {

	@Autowired
	private ScopedContentConditionAttributeRepository	repository;

	@Override
	public ScopedContentConditionAttributeRepository getRepository() {
		return this.repository;
	}
}
