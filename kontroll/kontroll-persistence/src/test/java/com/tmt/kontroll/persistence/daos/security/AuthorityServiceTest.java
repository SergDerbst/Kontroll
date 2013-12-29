package com.tmt.kontroll.persistence.daos.security;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tmt.kontroll.persistence.commons.config.PersistenceConfigTest;
import com.tmt.kontroll.persistence.commons.utils.impl.SecurityTestUtils;
import com.tmt.kontroll.persistence.daos.AbstractCrudServiceIntegerIdTest;
import com.tmt.kontroll.persistence.entities.security.Authority;
import com.tmt.kontroll.persistence.entities.security.User;
import com.tmt.kontroll.persistence.repositories.security.AuthorityRepository;

@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(classes = {PersistenceConfigTest.class})
public class AuthorityServiceTest extends AbstractCrudServiceIntegerIdTest<Authority, AuthorityRepository, AuthorityDao> {

	private static final int	Number_Of_Extra_Initial_Entities	= 1;

	@Autowired
	private AuthorityDao	service;

	public AuthorityServiceTest() {
		super(Number_Of_Extra_Initial_Entities);
	}

	@Override
	protected AuthorityDao getDaoService() {
		return service;
	}

	@Ignore
	@Override
	public void test_delete() {
		/*
		 * Ignored due to the way cascading works with many to many relations.
		 * In this case the relation is handled by the user entity, so
		 * trying to delete an authority entity will lead to an integrity 
		 * constraint violation.
		 */
	}

	@Override
	protected Authority getEntityToSave() {
		final Authority a = new Authority();
		a.setComment(SecurityTestUtils.Dummy_String);
		a.setId(Id_1st);
		a.setName("a1");
		a.setUsers(new ArrayList<User>() {
			private static final long	serialVersionUID	= 4634480428157192267L;
			{
				add(SecurityTestUtils.fetchUserEntity());
			}
		});
		return a;
	}

	@Override
	protected List<Authority> getEntitiesToSave() {
		final Authority a = new Authority();
		a.setComment(SecurityTestUtils.Dummy_String);
		a.setId(Id_2nd);
		a.setName("a2");
		a.setUsers(new ArrayList<User>() {
			private static final long	serialVersionUID	= 4634480428157192267L;
			{
				add(SecurityTestUtils.fetchUserEntity());
			}
		});
		return new ArrayList<Authority>() {
			private static final long	serialVersionUID	= -6932050338626796865L;
			{
				add(getEntityToSave());
				add(a);
			}
		};
	}
}