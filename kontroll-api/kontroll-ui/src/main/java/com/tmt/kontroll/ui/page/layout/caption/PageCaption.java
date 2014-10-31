package com.tmt.kontroll.ui.page.layout.caption;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.layout.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} contains a caption as content. The caption identifier
 * must be provided as {@link #value}.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageCaption {

	String value();
}
