package com.tmt.kontroll.ui.page.configuration.annotations.components.containers;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} is a scrollable container. When rendered,
 * it will consist of two divs. The scrollability has to be defined in the proper style classes.
 * The first div can be styled in the css class generated from the full scope name. The second
 * class as well, but it will also have an additional default class <code>scroller</code> attached
 * to assure the scrolling functionality.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ConfigurationAnnotation
@Documented
public @interface Scrollable {

}
