package com.tmt.kontroll.ui.page.configuration.enums.components;

import com.tmt.kontroll.ui.page.configuration.annotations.config.ItemsSource;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configuration enum for the {@link ItemsSource} annotation.
 *
 * @author SergDerbst
 *
 */
public enum ItemSourceType {

	/**
	 * Indicates that the items array is directly given in the source value (separated by commas).
	 */
	Direct,
	/**
	 * Indicates that the items array is given as a property of the according {@link PageSegment}.
	 * The source value then denotes the property name.
	 */
	Property,
	/**
	 * Indicates that the items array is a property of a different segment than the annotated one. If
	 * so, the source value consists of the full scope name of that segment plus the propety path to
	 * the array within that segment, both separated by a <code>#</code>.
	 */
	Path,
	/**
	 * Indicates that the items array shall be requested via a url. The source value then is that url,
	 * which be either a request path starting with a slash (then the request goes to the application's
	 * server) or a full url to any given 3rd party server.
	 */
	Url,
	/**
	 * Indicates that items array is not handled by the Kontroll framework, but by the app itself.
	 */
	Custom;
}
