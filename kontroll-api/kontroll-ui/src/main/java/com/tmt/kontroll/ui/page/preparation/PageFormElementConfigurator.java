package com.tmt.kontroll.ui.page.preparation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.PageConfigurationFailedException;
import com.tmt.kontroll.ui.page.components.form.Form;
import com.tmt.kontroll.ui.page.components.form.elements.InputElement;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.layout.PageSegment;

/**
 * The page form element configurator configures {@link PageSegment}s annotated as being
 * forms or input elements.
 *
 * @author SergDerbst
 *
 */
@Component
public class PageFormElementConfigurator {

	public void configure(final PageSegment segment) {
		this.configureForm(segment);
		this.configureInputElement(segment);
	}

	private void configureInputElement(final PageSegment segment) {
		try {
			for (final Annotation annotation : segment.getClass().getDeclaredAnnotations()) {
				if (annotation.annotationType().isAnnotationPresent(InputElement.class)) {
					for (final Field field : annotation.annotationType().getFields()) {
						segment.getAttributes().put(field.getName(), (String) field.get(annotation));
					}
				}
			}
		} catch (final Exception e) {
			throw new PageConfigurationFailedException(e);
		}
	}

	private void configureForm(final PageSegment segment) {
		final Form form = segment.getClass().getAnnotation(Form.class);
		if (form != null) {
			segment.getAttributes().put("name", form.name());
			final PageEvent event = new PageEvent(EventType.Submit, "submit");
			event.getArguments().put("targetUrl", form.targetUrl());
			segment.getGeneralEvents().put(EventType.Submit, event);
		}
	}
}
