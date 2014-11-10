package com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta-annotation to indicated that the annotated annotation is an input element.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormControl {

}
