package com.tmt.kontroll.context.session;

import java.util.TreeMap;

import org.springframework.stereotype.Component;

@Component
public class SessionContextHolder {

	TreeMap<String, SessionContext>	sessionContextMap	= new TreeMap<>();

	public SessionContext addSessionContext(final String sessionId) {
		SessionContext sessionContext = this.sessionContextMap.get(sessionId);
		if (sessionContext == null) {
			sessionContext = new SessionContext();
			this.sessionContextMap.put(sessionId, sessionContext);
		}
		return sessionContext;
	}

	public SessionContext sessionContext(final String sessionId) {
		return this.sessionContextMap.get(sessionId);
	}
}