package com.tmt.kontroll.ui.page.configuration.annotations.event;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;

/**
 * Defines an argument passed to the handler of the configured {@link Event}.
 *
 * @author SergDerbst
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConfigurationAnnotation
public @interface HandlerArgument {

	String key();

	String value();
}
