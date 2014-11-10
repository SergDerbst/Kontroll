package com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;

/**
 * Indicates that the annotated {@link PageSegment} is a form control of type label.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FormControl
public @interface LabelControl {

	/**
	 * Indicates which control element this label is associated with.
	 *
	 * @return
	 */
	String controlElement();

	/**
	 * The HTML tag for the label control. Defaults to <code>label</select>.
	 *
	 * @return
	 */
	HtmlTag tag() default HtmlTag.Label;
}
