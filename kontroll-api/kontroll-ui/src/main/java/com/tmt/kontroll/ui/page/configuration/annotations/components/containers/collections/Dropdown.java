package com.tmt.kontroll.ui.page.configuration.annotations.components.containers.collections;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.event.Trigger;
import com.tmt.kontroll.ui.page.configuration.annotations.layout.ChildElement;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.configuration.helpers.providers.ItemProvider;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * <p>
 * Indicates that the annotated {@link PageSegment} contains a child element which is a drop down list.
 * </p>
 * A drop down list by default is invisible, but toggled by another event on another element. It is
 * always rendered as an unordered list, wherase each list element contains another page segment. These
 * segments have to be provided by a given {@link ItemProvider}.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(Dropdowns.class)
@ChildElement
@ConfigurationAnnotation
public @interface Dropdown {

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
	 * The triggers that toggle the dropdown list.
	 *
	 * @return
	 */
	Trigger[] triggers();

	/**
	 * The {@link ItemProvider}.
	 *
	 * @return
	 */
	Class<? extends ItemProvider> itemProvider();

	/**
	 * The default visibility.
	 *
	 * @return
	 */
	boolean visible() default false;
}
