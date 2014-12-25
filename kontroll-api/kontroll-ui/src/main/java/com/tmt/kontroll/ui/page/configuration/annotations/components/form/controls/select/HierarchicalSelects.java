package com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.ContainerAnnotation;

/**
 * Container annotation to make {@link HierarchicalSelect} repeatable.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConfigurationAnnotation
@ContainerAnnotation
public @interface HierarchicalSelects {

	HierarchicalSelect[] value();
}
