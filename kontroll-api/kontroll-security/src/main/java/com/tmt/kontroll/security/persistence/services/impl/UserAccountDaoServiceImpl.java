package com.tmt.kontroll.security.persistence.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;
import com.tmt.kontroll.security.persistence.entities.UserAccount;
import com.tmt.kontroll.security.persistence.repositories.UserAccountRepository;
import com.tmt.kontroll.security.persistence.services.UserAccountDaoService;

@Service
public class UserAccountDaoServiceImpl extends BaseCrudDaoService<UserAccountRepository, UserAccount> implements UserAccountDaoService {

	@Autowired
	UserAccountRepository	repository;

	@Override
	public UserAccountRepository getRepository() {
		return this.repository;
	}

	@Override
	public UserAccount findByName(final String name) {
		return this.repository.findByName(name);
	}
}
