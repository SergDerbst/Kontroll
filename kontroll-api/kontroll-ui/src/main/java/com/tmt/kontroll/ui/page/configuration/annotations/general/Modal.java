package com.tmt.kontroll.ui.page.configuration.annotations.general;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} is a modal dialog.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConfigurationAnnotation
public @interface Modal {

	/**
	 * The css class to be used to style the modal dialog. By default the standard
	 * css class <i>modalDialog</i> will be used.
	 *
	 * @return
	 */
	String cssClass() default "modalDialog";
}
