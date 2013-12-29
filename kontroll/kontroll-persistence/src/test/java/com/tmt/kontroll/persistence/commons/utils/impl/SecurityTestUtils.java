package com.tmt.kontroll.persistence.commons.utils.impl;

import java.util.ArrayList;

import com.tmt.kontroll.persistence.commons.utils.EntityTestUtils;
import com.tmt.kontroll.persistence.entities.security.Authority;
import com.tmt.kontroll.persistence.entities.security.User;

public class SecurityTestUtils extends EntityTestUtils {

	public static Authority fetchAuthorityEntity() {
		final Authority a = new Authority();
		a.setComment(Dummy_String);
		a.setId(Dummy_Integer);
		a.setName(Dummy_String);
		a.setUsers(new ArrayList<User>() {
			private static final long	serialVersionUID	= -4413068252775177913L;
			{
				add(fetchUserEntity());
			}
		});
		return a;
	}

	public static User fetchUserEntity() {
		final User u = new User();
		u.setId(Dummy_Integer);
		u.setPassword(Dummy_String);
		u.setName(Dummy_String);
		return u;
	}
}
