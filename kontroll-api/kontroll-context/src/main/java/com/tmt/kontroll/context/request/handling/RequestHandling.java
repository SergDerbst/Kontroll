package com.tmt.kontroll.context.request.handling;

import com.tmt.kontroll.context.annotations.RequestHandler;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;

/**
 * <p>
 * Indicates how the {@link RequestHandlingService} annotated with {@link @RequestHandler}
 * is used for the handling of requests matching the pattern given under {@link RequestHandler#patterns}.
 * </p>
 * <p>
 * <ul>
 * <li>
 * The value <code>Always</code> denotes that the service has to be called always during processing a matching request,
 * but after services denoted with {@link #AlwaysFirst} and before those denoted with {@link #AlwaysLast}.
 * </li>
 * <li>
 * The value <code>AlwaysFirst</code> denotes that the service has to be called always before <i>any</i> services denoted
 * any other way. The order of calling of {@link #AlwaysFirst} services is determined by how they are
 * configured using {@link RequestHandler#preHandling} and {@link RequestHandler#postHandling}.
 * </li>
 * <li>
 * The value <code>AlwaysLast</code> denotes that the service has to be called always <i>after</i> any services denoted any
 * other way. The order of calling of {@link #AlwaysLast} services is determined by how they are configured
 * using {@link RequestHandler#preHandling} and {@link RequestHandler#postHandling}.
 * </li>
 * <li>
 * The value <code>Optional</code> denotes that the service only has to be called, if it is part of the configuration in
 * {@link RequestHandler#preHandling} and {@link RequestHandler#postHandling} of another service denoted as
 * {@link #Always} or {@link #AlwaysLast}.
 * </li>
 * </ul>
 * </p>
 * @author SergDerbst
 *
 */
public enum RequestHandling {
	Always,
	AlwaysFirst,
	AlwaysLast,
	Optional;
}
