package com.tmt.kontroll.context.session;

import java.util.Locale;

import com.tmt.kontroll.context.request.data.json.DataTransferElement;
import com.tmt.kontroll.security.persistence.entities.User;

/**
 * Contains all data relevant to the current session.
 *
 * @author SergDerbst
 *
 */
public class SessionContext implements DataTransferElement {

	private Locale	locale;
	private User		user;

	@Override
	public String getDtoClass() {
		return this.getClass().getName();
	}

	public Locale getLocale() {
		return this.user == null ? this.locale : this.user.getLocale();
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}
}
