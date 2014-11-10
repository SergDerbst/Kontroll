package com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment}, which must be a form control,
 * has a label associated with it. The value depicts the caption identifier of this
 * label.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Label {

	String value() default "";
}
