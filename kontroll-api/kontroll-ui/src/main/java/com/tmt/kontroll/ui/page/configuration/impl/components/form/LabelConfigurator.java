package com.tmt.kontroll.ui.page.configuration.impl.components.form;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label.Label;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.configuration.helpers.creators.LabelCreator;
import com.tmt.kontroll.ui.page.configuration.impl.components.layout.ChildElementConfigurator;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configurator for {@link Label} annotation. It either adds a label child element to
 * the given segment or it adds a label child element to the given segment's parent,
 * depending if the annotated segment is a {@link FormControl} or not.
 *
 * @author SergDerbst
 *
 */
@Component
public class LabelConfigurator extends ChildElementConfigurator {

	@Autowired
	LabelCreator	labelCreator;

	public LabelConfigurator() {
		super(Label.class);
	}

	@Override
	public void configure(final PageSegment segment) {
		final PageSegment label = this.labelCreator.create(segment);
		if (!this.isFormControl(segment)) {
			super.addChild(this.retrieveChildPosition(label, segment), segment, label);
		}
	}

	private ChildPosition retrieveChildPosition(final PageSegment label, final PageSegment segment) {
		for (final Annotation annotation : segment.getClass().getAnnotations()) {
			try {
				final Method nameMethod = annotation.annotationType().getMethod("name");
				final String name = (String) nameMethod.invoke(annotation);
				if (label.getAttributes().get("for").equals(name)) {
					final Method positionMethod = annotation.annotationType().getMethod("position");
					return (ChildPosition) positionMethod.invoke(annotation);
				}
			} catch (final Exception e) {
				continue;
			}
		}
		return null;
	}

	private boolean isFormControl(final PageSegment segment) {
		for (final Annotation annotation : segment.getClass().getAnnotations()) {
			if (annotation.annotationType().isAnnotationPresent(FormControl.class)) {
				return true;
			}
		}
		return false;
	}
}
