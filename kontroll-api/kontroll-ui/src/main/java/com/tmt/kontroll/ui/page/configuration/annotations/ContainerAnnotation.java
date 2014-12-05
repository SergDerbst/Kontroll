package com.tmt.kontroll.ui.page.configuration.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta-annotation to mark annotations as container annotations used to make
 * other annotations repeatable.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ContainerAnnotation {

}
