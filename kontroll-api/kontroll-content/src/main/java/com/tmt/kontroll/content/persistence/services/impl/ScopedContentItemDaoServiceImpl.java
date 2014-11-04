package com.tmt.kontroll.content.persistence.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.tmt.kontroll.business.annotations.BusinessService;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentItemRepository;
import com.tmt.kontroll.content.persistence.services.ScopedContentItemDaoService;
import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;

@Transactional
@BusinessService
public class ScopedContentItemDaoServiceImpl extends BaseCrudDaoService<ScopedContentItemRepository, ScopedContentItem> implements ScopedContentItemDaoService {

	@Autowired
	private ScopedContentItemRepository	repository;

	@Override
	public ScopedContentItemRepository getRepository() {
		return this.repository;
	}
}
