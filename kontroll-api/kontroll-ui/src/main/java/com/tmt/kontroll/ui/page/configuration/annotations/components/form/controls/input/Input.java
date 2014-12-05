package com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label.Label;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ValueSource;
import com.tmt.kontroll.ui.page.configuration.annotations.layout.ChildElement;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} contains an input element as child.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ChildElement
@ConfigurationAnnotation
@Documented
@Repeatable(Inputs.class)
public @interface Input {

	/**
	 * The child element's name used as suffix to the parent element's scope name in order to
	 * generate the child element's full scope name.
	 *
	 * @return
	 */
	String name();

	ChildPosition position();

	/**
	 * The ordinal number within top and bottom child elements on the annotated segment. The elements will be
	 * ordered according to this attribute, however main children will always be put in between top and
	 * bottom children.
	 *
	 * @return
	 */
	int ordinal();

	/**
	 * The input control's type. Defaults to text.
	 *
	 * @return
	 */
	String type() default "text";

	/**
	 * Whether or not the input element is readonly. Defaults to false.
	 *
	 * @return
	 */
	boolean readonly() default false;

	/**
	 * Whether or not the input element is hidden. Defaults to false.
	 *
	 * @return
	 */
	boolean hidden() default false;

	ValueSource valueSource() default @ValueSource;

	Label label() default @Label;
}
