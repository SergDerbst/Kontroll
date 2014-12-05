package com.tmt.kontroll.ui.page.configuration.annotations.config;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.components.containers.collections.List;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.Select;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.SelectControl;
import com.tmt.kontroll.ui.page.configuration.enums.components.ItemSourceType;

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

	String source() default "";
}
