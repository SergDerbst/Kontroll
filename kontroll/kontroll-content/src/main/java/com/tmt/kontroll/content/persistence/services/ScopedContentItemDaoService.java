package com.tmt.kontroll.content.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.content.persistence.repositories.ScopedContentItemRepository;
import com.tmt.kontroll.persistence.daos.AbstractCrudDao;

@Service
public class ScopedContentItemDaoService extends AbstractCrudDao<ScopedContentItemRepository, ScopedContentItem, Integer> {

	@Autowired
	private ScopedContentItemRepository	repository;

	@Override
	public ScopedContentItemRepository getRepository() {
		return this.repository;
	}
}
