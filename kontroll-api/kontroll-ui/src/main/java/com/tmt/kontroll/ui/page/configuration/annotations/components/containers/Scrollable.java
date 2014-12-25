package com.tmt.kontroll.ui.page.configuration.annotations.components.containers;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} is a scrollable container. The configurator
 * will handle this as two divs, whereas the outter div serves as container and the inner div
 * as scroller. The scrollability is handled via css via classes <code>scrollContainer</code>
 * and <code>scroller</code>, respectively.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConfigurationAnnotation
public @interface Scrollable {

}
