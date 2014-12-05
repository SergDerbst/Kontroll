package com.tmt.kontroll.context.request.handling.services;

import com.tmt.kontroll.context.request.handling.RequestHandlingParam;

/**
 * Abstract base class for all request handler services. The are business level
 * services that are responsible for retrieving and manipulating data of given
 * request contexts.
 *
 * @author Sergio Weigel
 *
 */
public interface RequestHandlingService {

	void handle(final RequestHandlingParam param);
}
