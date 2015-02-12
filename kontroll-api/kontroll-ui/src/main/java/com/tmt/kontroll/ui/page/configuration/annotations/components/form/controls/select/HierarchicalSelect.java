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
import com.tmt.kontroll.ui.page.configuration.annotations.layout.ChildElement;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * <p>
 * Indicates that the annotated {@link PageSegment} will have two select child elements
 * attached to it, whereas the first will be the master and the second the slave. Contrary
 * to the standard {@link Select} element, it does not have an {@link ItemsSource} given.
 * Instead, the annotated page segment was provide a property called <code>optionsMap</code>,
 * which is a map of strings as keys and string arrays as values.
 * </p>
 * <p>
 * The master, having all the keys of the map as options, determines
 * which options are selectable in the slave select options (the string array stored under
 * the selected master slave value as key in the options map).
 * </p>
 * <p>
 * Both master and slave label's will be generated as captions from their full scope names.
 * This is a convenience way of creating interdependent select controls. For any more specific
 * configuration, the select elements have to be created separately.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ChildElement
@ConfigurationAnnotation
@Documented
@Repeatable(HierarchicalSelects.class)
public @interface HierarchicalSelect {

	/**
	 * The child element's name used as suffix to the parent element's scope name in order to
	 * generate the child element's full scope name. The master select control will have
	 * <code>Master</code> attached to it, and the slave select control will have <code>Slave</code>
	 * attached to it.
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
	 * The value name of the master select input element.
	 *
	 * @return
	 */
	String master();

	/**
	 * The value name of the slave select input element.
	 *
	 * @return
	 */
	String slave();

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
	 * The source of the options map.
	 *
	 * @return
	 */
	ItemsSource optionsSource() default @ItemsSource(source = "optionsMap");

	Label label() default @Label;
}
