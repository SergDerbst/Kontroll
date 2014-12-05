package com.tmt.kontroll.ui.page.configuration.enums.components;

import com.tmt.kontroll.ui.page.configuration.annotations.config.ValueSource;

/**
 * Configuration enum for {@link ValueSource} annotation.
 *
 * @author SergDerbst
 *
 */
public enum ValueSourceType {
	/**
	 * The source value is taken as given.
	 */
	Plain,
	/**
	 * The source value is taken from another scope relatively from the target scope,
	 * whereas <i>parent</i> indicates one step up in the scope hierarchy.
	 */
	Path,
	/**
	 * The source value is taken from an argument parsed to the handler/preparer or
	 * other frontend module responsible for rendering the element.
	 */
	Arg,
	/**
	 * The source value is taken from a caption, hence the value should be a caption
	 * identifier or, if left empty, it will be the caption identifier automatically
	 * generated from the page segment's scope name.
	 */
	Caption,
	/**
	 * The source value is taken from a url. This can be both a request path, then it is
	 * requested for from the application's server, or a full url to a 3rd party server.
	 */
	Url;
}
