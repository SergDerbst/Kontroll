package com.tmt.kontroll.ui.page.configuration.annotations.components.text;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.layout.ChildElement;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} contains an header element as child.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ChildElement
@ConfigurationAnnotation
@Documented
@Repeatable(Headers.class)
public @interface Header {

	/**
	 * The child element's name used as suffix to the parent element's scope name in order to
	 * generate the child element's full scope name.
	 *
	 * @return
	 */
	String name();

	ChildPosition position();

	/**
	 * The ordinal number within all child elements on the annotated segment. The elements will be
	 * ordered according to this attribute.
	 *
	 * @return
	 */
	int ordinal();

	/**
	 * The caption id to be used for this header. If left empty, the generated child element's
	 * scope name will be used with ".caption" suffix.
	 *
	 * @return
	 */
	String caption() default "";

	/**
	 * The level of the header element (1 will be rendered to h1, 2 to h2 etc.).
	 * @return
	 */
	int level() default 1;
}