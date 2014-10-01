package com.tmt.kontroll.ui.page.management.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.ui.page.layout.PageSegment;

/**
 * <p>
 * Indicates to which request paths and page scopes the annotated {@link PageSegment} belongs to
 * and under which conditions it will be integrated into the page.
 * </p>
 * <p>
 * If a regex pattern contained in {@link #patterns} matches the path of an incoming request
 * and if a scope name contained in {@link #scopes} matches an existing scope, the annotated
 * page segment will be included to the page to be rendered, if the given {@link Condition}s
 * are met.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PageContext {

	/**
	 * The full name of the scope, e.g. "header.menubar.menuitems.home" annotates the {@link PageSegment}
	 * as the menubar item to navigate to the home page.
	 *
	 * @return
	 */
	String scope();

	/**
	 * The ordinal number by which {@link PageSegment}s are ordered as children of their parent page segment.
	 *
	 * @return
	 */
	int ordinal() default 0;

	/**
	 * A regex expression to annotate the request paths, for which the {@link PageSegment} should be shown.
	 * By default, the segment will be shown for all requests.
	 *
	 * @return
	 */
	String pattern() default "/";

	/**
	 * An array of {@link Condition}s that have to be true in order for the {@link PageSegment} to be shown.
	 * By default, no conditions have to be met.
	 *
	 * @return
	 */
	Condition[] conditions() default {};

	/**
	 * The {@link ConditionalOperator} that will be applied to verify the {@link Condition}s given above. By
	 * default, the conditions will be linked by a boolean AND.
	 *
	 * @return
	 */
	ConditionalOperator operator() default ConditionalOperator.And;
}
