package com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;

/**
 * Indicates that the annotated {@link PageSegment} is a form control of type select.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FormControl
public @interface SelectControl {

	/**
	 * The input element's name.
	 *
	 * @return
	 */
	String name();

	/**
	 * The HTML tag for the select control. Defaults to <code>select</select>.
	 *
	 * @return
	 */
	HtmlTag tag() default HtmlTag.Select;
}
