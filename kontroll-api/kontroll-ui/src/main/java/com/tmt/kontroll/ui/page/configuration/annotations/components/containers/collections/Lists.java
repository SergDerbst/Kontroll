package com.tmt.kontroll.ui.page.configuration.annotations.components.containers.collections;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.ContainerAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.layout.ChildElement;

/**
 * Container annotation for {@link List} to make it repeatable.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ChildElement
@Documented
@ConfigurationAnnotation
@ContainerAnnotation
public @interface Lists {

	List[] value();
}
