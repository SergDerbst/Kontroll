package com.tmt.kontroll.ui.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.layout.PageSegment;

/**
 * A {@link PageSegment} annotated with this annotation will have the css
 * classes given in {@link #value} additional to the standard ones determined by
 * the segment's scope name.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CssClass {

	String value();
}
