package com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label.Label;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ItemsSource;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ValueSource;
import com.tmt.kontroll.ui.page.configuration.annotations.layout.ChildElement;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} has a select element as child element.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ChildElement
@ConfigurationAnnotation
@Documented
@Repeatable(Selects.class)
public @interface Select {

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

	/**
	 * The source of the options array.
	 *
	 * @return
	 */
	ItemsSource optionsSource() default @ItemsSource;

	/**
	 * The source of the value of the select element (for pre-selection).
	 *
	 * @return
	 */
	ValueSource valueSource() default @ValueSource;

	Label label() default @Label;
}
