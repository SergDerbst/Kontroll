package com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} is an input element of type file.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FormControl
@ConfigurationAnnotation
@Documented
public @interface FileControl {

	/**
	 * The input element's name.
	 *
	 * @return
	 */
	String name();

	/**
	 * The HTML tag for the select control. Defaults to <code>input</select>.
	 *
	 * @return
	 */
	HtmlTag tag() default HtmlTag.Input;
}
