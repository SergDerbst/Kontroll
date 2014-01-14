package com.tmt.kontroll.persistence.daos.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tmt.kontroll.persistence.commons.config.PersistenceTestConfig;
import com.tmt.kontroll.persistence.commons.utils.impl.SecurityTestUtils;
import com.tmt.kontroll.persistence.daos.AbstractCrudServiceIntegerIdTest;
import com.tmt.kontroll.persistence.entities.security.Authority;
import com.tmt.kontroll.persistence.entities.security.User;
import com.tmt.kontroll.persistence.repositories.security.UserRepository;

@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(classes = { PersistenceTestConfig.class })
public class UserServiceTest extends
		AbstractCrudServiceIntegerIdTest<User, UserRepository, UserDao> {

	@Autowired
	private UserDao service;

	public UserServiceTest() {
		super(1);
	}

	@Override
	protected UserDao getDaoService() {
		return service;
	}

	@Override
	protected User getEntityToSave() {
		final User u = new User();
		u.setAuthorities(new ArrayList<Authority>() {
			private static final long serialVersionUID = -8056428504768663546L;
			{
				add(SecurityTestUtils.fetchAuthorityEntity());
			}
		});
		u.setId(Id_1st);
		u.setPassword(SecurityTestUtils.Dummy_String);
		u.setName("u1");
		return u;
	}

	@Override
	protected List<User> getEntitiesToSave() {
		final User u = new User();
		u.setAuthorities(new ArrayList<Authority>() {
			private static final long serialVersionUID = -8056428504768663546L;
			{
				add(SecurityTestUtils.fetchAuthorityEntity());
			}
		});
		u.setId(Id_2nd);
		u.setPassword(SecurityTestUtils.Dummy_String);
		u.setName("u2");
		return new ArrayList<User>() {
			private static final long serialVersionUID = 1093801036233114363L;
			{
				add(getEntityToSave());
				add(u);
			}
		};
	}
}