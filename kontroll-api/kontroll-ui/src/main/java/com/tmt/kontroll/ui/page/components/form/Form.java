package com.tmt.kontroll.ui.page.components.form;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.layout.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} is a form.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Form {

	/**
	 * The target url when the form submits.
	 *
	 * @return
	 */
	String targetUrl();

	/**
	 * The name of the form.
	 *
	 * @return
	 */
	String name();
}
