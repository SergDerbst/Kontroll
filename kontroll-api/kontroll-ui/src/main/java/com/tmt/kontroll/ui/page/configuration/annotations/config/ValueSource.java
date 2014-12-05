package com.tmt.kontroll.ui.page.configuration.annotations.config;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.enums.components.ValueSourceType;

/**
 * Configuration annotation used for annotations that may have different sources
 * of values to be rendered or configured, respectively. The proper configurator
 * is responsible for handling the configuration accordingly.
 *
 * @author SergDerbst
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConfigurationAnnotation
public @interface ValueSource {

	ValueSourceType type() default ValueSourceType.Caption;

	String source() default "";
}