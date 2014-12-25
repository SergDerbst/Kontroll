package com.tmt.kontroll.context.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Container annotation to make {@link RequestHandler} repeatable.
 *
 * @author SergDerbst
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestHandlers {

	RequestHandler[] value();
}
