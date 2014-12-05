package com.tmt.kontroll.ui.page.configuration.enums.components;

import com.tmt.kontroll.ui.page.configuration.annotations.layout.ChildElement;

/**
 * Configuration enum for annotations annotated with {@link ChildElement}. It is
 * used to de determine whether the element should be added before (on top) of the
 * main children elements or after (at the bottom).
 *
 * @author SergDerbst
 *
 */
public enum ChildPosition {

	Top,
	Bottom;
}
