package com.tmt.kontroll.ui.page.configuration.impl.general;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.general.CssClass;

/**
 * <p>
 * Configures the <code>class</code> attribute of {@link PageSegment}s.
 * </p>
 * <p>
 * The value of the <code>class</code> attribute is generated out of the scope name, whereas each
 * element of the name is added as an individual class. If the {@link PageSegment} is annotated
 * with {@link CssClass}, the annotation's value will be added to the <code>class</code> attribute
 * value.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class CssClassConfigurator extends PageSegmentConfigurator {

	@Override
	protected boolean isResponsible(final PageSegment segment) {
		return true;
	}

	@Override
	protected void doConfiguration(final PageSegment segment) {
		final String[] cssScopeClasses = segment.getDomId().split("\\.");
		final String additionalCssClass = segment.getClass().isAnnotationPresent(CssClass.class) ? segment.getClass().getAnnotation(CssClass.class).value() : "";
		String cssClassValue = "";
		for (int i = cssScopeClasses.length - 1; i >= 0; i--) {
			cssClassValue = cssClassValue + " " + cssScopeClasses[i];
		}
		if (!additionalCssClass.isEmpty()) {
			cssClassValue = cssClassValue + " " + additionalCssClass;
		}
		segment.getAttributes().put("class", cssClassValue);
	}
}
