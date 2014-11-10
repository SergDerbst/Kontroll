package com.tmt.kontroll.ui.page.configuration.impl.components.form;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.ui.exceptions.PageConfigurationFailedException;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;

/**
 * <p>
 * Configures {@link PageSegment}s annotated with annotations having {@link FormControl} as
 * meta-annotation.
 * </p>
 * <p>
 * It iterates over all annotations directly present on the page segment and for each form control
 * annotation it will be setting the tag according to their <code>tag</code> value as well as iterating over
 * all methods of the annotation and adding an attribute to the segment's attributes consisting of the
 * method's name as key and its returned value as value.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class FormControlConfigurator extends PageSegmentConfigurator {

	@Override
	protected boolean isResponsible(final PageSegment segment) {
		for (final Annotation annotation : segment.getClass().getDeclaredAnnotations()) {
			if (annotation.annotationType().isAnnotationPresent(FormControl.class)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void doConfiguration(final PageSegment segment) {
		try {
			for (final Annotation annotation : segment.getClass().getDeclaredAnnotations()) {
				if (annotation.annotationType().isAnnotationPresent(FormControl.class)) {
					for (final Method method : annotation.annotationType().getDeclaredMethods()) {
						if (HtmlTag.class.equals(method.getReturnType())) {
							segment.setTag((HtmlTag) method.invoke(annotation));
						} else {
							segment.getAttributes().put(method.getName(), (String) method.invoke(annotation));
						}
					}
				}
			}
		} catch (final Exception e) {
			throw new PageConfigurationFailedException(e);
		}
	}
}
