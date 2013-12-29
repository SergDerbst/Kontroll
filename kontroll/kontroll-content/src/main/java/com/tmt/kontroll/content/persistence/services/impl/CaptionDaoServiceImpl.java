package com.tmt.kontroll.content.persistence.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.content.persistence.repositories.CaptionRepository;
import com.tmt.kontroll.content.persistence.services.CaptionDaoService;
import com.tmt.kontroll.persistence.daos.AbstractCrudDao;

@Service
public class CaptionDaoServiceImpl extends AbstractCrudDao<CaptionRepository, Caption, Integer> implements CaptionDaoService {

	@Autowired
	private CaptionRepository repository;

	@Override
	public CaptionRepository getRepository() {
		return this.repository;
	}
}
