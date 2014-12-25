package com.tmt.kontroll.ui.page.configuration.helpers.handlers;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Handles the css class attribute of the given {@link PageSegment}. It checks if the
 * class attribute already exists and if the generated scope classes already exist. If
 * they don't, they will be added. It will then append the given css value to the class
 * attribute.
 *
 * @author SergDerbst
 *
 */
@Component
public class CssConfigurationHandler {

	public void handle(final PageSegment segment) {
		this.handle(segment, "");
	}

	public void handle(final PageSegment segment, final String css) {
		final StringBuilder builder = new StringBuilder();
		final String classValue = segment.getAttributes().get("class");
		if (classValue == null) {
			this.generateScopeBasedCssValue(segment, builder);
		} else {
			builder.append(classValue);
		}
		if (css != null && !css.isEmpty()) {
			builder.append(" " + css);
		}
		segment.getAttributes().put("class", builder.toString());
	}

	private void generateScopeBasedCssValue(final PageSegment segment, final StringBuilder builder) {
		final String[] cssScopeClasses = segment.getDomId().split("\\.");
		for (int i = 0; i < cssScopeClasses.length; i++) {
			builder.append(cssScopeClasses[i]);
			if (i < cssScopeClasses.length - 1) {
				builder.append(" ");
			}
		}
	}
}
