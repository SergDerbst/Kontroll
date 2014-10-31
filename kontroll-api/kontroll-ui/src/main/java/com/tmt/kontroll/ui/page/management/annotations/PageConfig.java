package com.tmt.kontroll.ui.page.management.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.events.Event;
import com.tmt.kontroll.ui.page.layout.PageSegment;

/**
 * Determines the page configuration for the annotated {@link PageSegment}.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageConfig {

	/**
	 * Specifies all page contexts, under which the annotated page segment should occur.
	 *
	 * @return
	 */
	PageContext[] contexts();

	/**
	 * Specifies all events and their handlers to be bound to the annotated page segment. The events
	 * specified here will be handled in all given page contexts. Should some events only occur
	 * in some specific contexts, they should be declared in the {@link PageContext} annotation.
	 *
	 * @return
	 */
	Event[] events() default {};
}
