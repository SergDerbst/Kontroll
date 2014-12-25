package com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.textarea;

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
import com.tmt.kontroll.ui.page.configuration.enums.components.WrapType;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} has a text area element as child element.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ChildElement
@ConfigurationAnnotation
@Documented
@Repeatable(Textareas.class)
public @interface Textarea {

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
	 * The number of rows of the text area, defaults to five.
	 *
	 * @return
	 */
	int rows() default 5;

	/**
	 * The number of columns of the text area, defaults to twenty.
	 *
	 * @return
	 */
	int cols() default 20;

	/**
	 * The value of the wrap attribute of the text area, defaults to virtual.
	 *
	 * @return
	 */
	WrapType wrap() default WrapType.Virtual;

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
	 * The source of the value of the select element (for pre-selection).
	 *
	 * @return
	 */
	ValueSource valueSource() default @ValueSource;

	Label label() default @Label;
}
