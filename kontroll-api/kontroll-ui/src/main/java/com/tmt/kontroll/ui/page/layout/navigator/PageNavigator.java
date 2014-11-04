package com.tmt.kontroll.ui.page.layout.navigator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.layout.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} is a page navigator. A navigator will handle a click event
 * by navigating to the path given in {@link #value}.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PageNavigator {

	String value();
}
