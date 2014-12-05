package com.tmt.kontroll.ui.page.configuration.annotations.components.containers.collections;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ItemsSource;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} is a list container. A list container consists
 * of a containing <code>div</code> element, a <code>ul</code> element and a set of <code>li</li>
 * elements, whose values will be determined according to the configuration of {@link #itemsSource}.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ConfigurationAnnotation
@Documented
public @interface ListContainer {

	ItemsSource itemsSource() default @ItemsSource;
}
