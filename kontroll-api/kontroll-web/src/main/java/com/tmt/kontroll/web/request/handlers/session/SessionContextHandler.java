package com.tmt.kontroll.web.request.handlers.session;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmt.kontroll.context.annotations.RequestHandler;
import com.tmt.kontroll.context.request.handling.RequestHandling;
import com.tmt.kontroll.context.request.handling.RequestHandlingParam;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;
import com.tmt.kontroll.context.session.SessionContext;
import com.tmt.kontroll.context.session.SessionContextHolder;

@RequestHandler(handling = RequestHandling.AlwaysFirst)
public class SessionContextHandler implements RequestHandlingService {

	@Autowired
	SessionContextHolder	sessionContextHolder;

	@Override
	public void handle(final RequestHandlingParam param) {
		final SessionContext sessionContext = this.sessionContextHolder.addSessionContext(param.getSession().getId());
		this.handleLocale(param.getRequest(), sessionContext);
	}

	private void handleLocale(final HttpServletRequest request, final SessionContext sessionContext) {
		final Locale locale = sessionContext.getLocale();
		if (locale == null) {
			sessionContext.setLocale(request.getLocale());
		}
	}
}