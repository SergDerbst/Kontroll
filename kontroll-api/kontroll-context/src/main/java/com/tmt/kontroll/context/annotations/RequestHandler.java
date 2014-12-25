package com.tmt.kontroll.context.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tmt.kontroll.context.request.handling.RequestHandling;
import com.tmt.kontroll.context.request.handling.coordination.RequestHandlingCoordinator;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;

/**
 * Configuration annotation for {@link RequestHandlingService} objects.
 *
 * @author SergDerbst
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Repeatable(RequestHandlers.class)
public @interface RequestHandler {

	/**
	 * The patterns of request paths the annotated handling service is associated with.
	 *
	 * @return
	 */
	String[] patterns() default {"/"};

	/**
	 * Determines how the {@link RequestHandlingCoordinator} coordinates with the annotated
	 * request handling service.
	 *
	 * @return
	 */
	RequestHandling handling() default RequestHandling.Optional;

	/**
	 * A list of classes of request handling services, that must run <i>prior</i> the annotated one.
	 *
	 * @return
	 */
	Class<? extends RequestHandlingService>[] preHandling() default {};

	/**
	 * A list of classes of request handling services, that must run <i>after</i> the annotated one.
	 *
	 * @return
	 */
	Class<? extends RequestHandlingService>[] postHandling() default {};

	/**
	 * An array of {@link RequestMethod}s the annotated handler is excluded from.
	 *
	 * @return
	 */
	RequestMethod[] excludedMethod() default {};
}
