package com.tmt.kontroll.ui.page.configuration.annotations.components.form;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} is a form.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConfigurationAnnotation
public @interface Form {

	/**
	 * The name of the form.
	 *
	 * @return
	 */
	String name();

	/**
	 * The target url when the form submits.
	 *
	 * @return
	 */
	String targetUrl();

	/**
	 * The target scope to be rendered in response to the form's submission.
	 *
	 * @return
	 */
	String targetScope() default "";

	/**
	 * The name of the event handler handling the submit event.
	 *
	 * @return
	 */
	String[] submitHandlers() default "submit";

	/**
	 * The class of the data transfer object associated with the form.
	 *
	 * @return
	 */
	Class<?> dtoClass();
}
