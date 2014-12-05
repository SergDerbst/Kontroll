package com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ItemsSource;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ValueSource;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} is a form control of type select.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FormControl
@ConfigurationAnnotation
@Documented
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

	/**
	 * The source of the options array.
	 *
	 * @return
	 */
	ValueSource valueSource() default @ValueSource;

	/**
	 * The source of the value of the select element (for pre-selection).
	 *
	 * @return
	 */
	ItemsSource optionsSource() default @ItemsSource;
}
