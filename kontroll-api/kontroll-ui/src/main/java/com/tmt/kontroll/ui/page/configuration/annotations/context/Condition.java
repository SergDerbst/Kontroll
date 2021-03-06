package com.tmt.kontroll.ui.page.configuration.annotations.context;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.content.persistence.selections.BooleanOperator;
import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;

/**
 * <p>
 * Indicates a condition that must be true for a page segment to be included to a page.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConfigurationAnnotation
public @interface Condition {

	String path();

	String value();

	BooleanOperator operator() default BooleanOperator.IsEqual;
}
