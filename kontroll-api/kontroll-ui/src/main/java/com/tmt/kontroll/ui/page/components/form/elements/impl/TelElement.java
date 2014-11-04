package com.tmt.kontroll.ui.page.components.form.elements.impl;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.components.form.elements.InputElement;
import com.tmt.kontroll.ui.page.layout.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} is an input element of type tel.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@InputElement
public @interface TelElement {

	/**
	 * The input element's name.
	 *
	 * @return
	 */
	String name();
}
