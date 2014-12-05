package com.tmt.kontroll.ui.page.configuration.annotations.components.devices.button;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.event.Event;
import com.tmt.kontroll.ui.page.configuration.annotations.layout.ChildElement;
import com.tmt.kontroll.ui.page.configuration.enums.components.ButtonSize;
import com.tmt.kontroll.ui.page.configuration.enums.components.ButtonType;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} contains a button element as child.
 * It will be rendered as a div with the given css classes attached.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ChildElement
@ConfigurationAnnotation
@Documented
@Repeatable(Buttons.class)
public @interface Button {

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

	ButtonType type() default ButtonType.Button;

	/**
	 * The caption id to be used for this header. If left empty, the generated child element's
	 * scope name will be used with ".caption" suffix.
	 *
	 * @return
	 */
	String caption() default "";

	ButtonSize size() default ButtonSize.Medium;

	/**
	 * A list of events to be handled.
	 *
	 * @return
	 */
	Event[] events() default {};

	/**
	 * Extra css classes that might be applied. Defaults to empty string.
	 *
	 * @return
	 */
	String cssClass() default "";
}
