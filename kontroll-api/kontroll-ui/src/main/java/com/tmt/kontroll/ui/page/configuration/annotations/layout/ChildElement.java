package com.tmt.kontroll.ui.page.configuration.annotations.layout;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta-annotation to indicate that the annotated annotation is a child element
 * to be used within the {@link Children} annotation.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChildElement {

}
