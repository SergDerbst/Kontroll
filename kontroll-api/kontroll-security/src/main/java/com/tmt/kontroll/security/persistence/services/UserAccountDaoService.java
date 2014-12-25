package com.tmt.kontroll.security.persistence.services;

import com.tmt.kontroll.persistence.daos.CrudDao;
import com.tmt.kontroll.security.persistence.entities.UserAccount;

public interface UserAccountDaoService extends CrudDao<UserAccount, Integer> {

	UserAccount findByName(String name);
}
