package com.tmt.kontroll.content.persistence.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.content.persistence.repositories.CaptionRepository;
import com.tmt.kontroll.content.persistence.services.CaptionDaoService;
import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;

@Service
public class CaptionDaoServiceImpl extends BaseCrudDaoService<CaptionRepository, Caption> implements CaptionDaoService {

	@Autowired
	CaptionRepository repository;

	@Override
	public CaptionRepository getRepository() {
		return this.repository;
	}
}
