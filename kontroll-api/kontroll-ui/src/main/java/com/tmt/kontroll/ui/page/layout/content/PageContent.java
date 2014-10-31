package com.tmt.kontroll.ui.page.layout.content;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.ui.page.layout.PageSegment;

/**
 * Indicates that the annotated {@link PageSegment} has content.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageContent {

}
