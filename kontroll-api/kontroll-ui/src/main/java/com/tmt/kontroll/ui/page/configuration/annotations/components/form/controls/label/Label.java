package com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ValueSource;

/**
 * Configuration annotation for labels.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ConfigurationAnnotation
@Documented
public @interface Label {

	ValueSource value() default @ValueSource;
}
