package com.tmt.kontroll.context.session;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionContextHandler {

	@Autowired
	SessionContextHolder	sessionContextHolder;

	public void handle(final HttpSession httpSession, final HttpServletRequest request) {
		final SessionContext sessionContext = this.sessionContextHolder.addSessionContext(httpSession.getId());
		this.handleLocale(request, sessionContext);
	}

	private void handleLocale(final HttpServletRequest request, final SessionContext sessionContext) {
		final Locale locale = sessionContext.getLocale();
		if (locale == null) {
			sessionContext.setLocale(request.getLocale());
		}
	}
}