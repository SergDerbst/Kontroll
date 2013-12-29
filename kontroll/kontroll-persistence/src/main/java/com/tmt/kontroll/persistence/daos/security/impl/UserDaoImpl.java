package com.tmt.kontroll.persistence.daos.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.persistence.daos.AbstractCrudDao;
import com.tmt.kontroll.persistence.daos.security.UserDao;
import com.tmt.kontroll.persistence.entities.security.User;
import com.tmt.kontroll.persistence.repositories.security.UserRepository;

@Service
public class UserDaoImpl extends AbstractCrudDao<UserRepository, User, Integer> implements UserDao {

	@Autowired
	private UserRepository	repository;

	@Override
	public UserRepository getRepository() {
		return repository;
	}
}
