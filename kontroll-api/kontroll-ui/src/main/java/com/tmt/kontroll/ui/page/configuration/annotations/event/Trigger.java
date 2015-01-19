package com.tmt.kontroll.ui.page.configuration.annotations.event;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.events.EventType;

/**
 * Configuration annotation to point to an event trigger.
 *
 * @author SergDerbst
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConfigurationAnnotation
public @interface Trigger {

	/**
	 * The name of the scope element on which the event will happen.
	 *
	 * @return
	 */
	String value();

	EventType type() default EventType.Click;
}
