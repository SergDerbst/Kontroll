package com.tmt.kontroll.ui.page.configuration.annotations.content;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.ui.page.PageSegment;

/**
 * <p>
 * Indicates that the annotated {@link PageSegment} has content. An initial content can be specified
 * by its {@link ContentType} and {@link #content}..
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Content {

	ContentType type() default ContentType.Text;

	String content() default "";
}
