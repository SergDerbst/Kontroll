package com.tmt.kontroll.ui.page.configuration.annotations.content;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} contains a caption as content. The caption identifier
 * must be provided as {@link #value}.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Caption {

	String value();
}
