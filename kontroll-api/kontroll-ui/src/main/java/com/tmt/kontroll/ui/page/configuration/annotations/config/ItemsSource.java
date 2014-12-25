package com.tmt.kontroll.ui.page.configuration.annotations.config;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.components.containers.collections.List;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.Select;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.SelectControl;
import com.tmt.kontroll.ui.page.configuration.enums.components.ItemSourceType;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configuration annotation used for example in {@link List}, {@link Select} and
 * {@link SelectControl} to determine where the frontend module responsible for
 * rendering shall retrieve the array of items from.
 *
 * @author SergDerbst
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConfigurationAnnotation
public @interface ItemsSource {

	ItemSourceType type() default ItemSourceType.Property;

	/**
	 * If {@link ItemSourceType#PropertyOptionsMap} is selected for {@link #type}, the
	 * values can only be <code>keys</code> or <code>values</code> in order for the options
	 * function to work. This logic is to render two interdependent lists, where one is
	 * the master (with value <code>keys</code>) and the other the slave (with value
	 * <code>values</code>), whereas the master contains all keys and the slave the array
	 * as value stored under the selected key of the <code>optionsMap</code> property of
	 * the annotated {@link PageSegment}.
	 *
	 * @return
	 */
	String source() default "";
}
