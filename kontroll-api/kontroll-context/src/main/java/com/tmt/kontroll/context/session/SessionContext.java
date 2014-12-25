package com.tmt.kontroll.context.session;

import java.util.Locale;

import com.tmt.kontroll.context.request.data.json.DataTransferElement;
import com.tmt.kontroll.security.persistence.entities.UserAccount;

/**
 * Contains all data relevant to the current session.
 *
 * @author SergDerbst
 *
 */
public class SessionContext implements DataTransferElement {

	private Locale			locale;
	private UserAccount	userAccount;

	@Override
	public String getDtoClass() {
		return this.getClass().getName();
	}

	public Locale getLocale() {
		return this.userAccount == null ? this.locale : this.userAccount.getLocale();
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}
}
