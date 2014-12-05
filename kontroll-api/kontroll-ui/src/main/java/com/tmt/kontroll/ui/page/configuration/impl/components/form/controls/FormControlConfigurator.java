package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.exceptions.PageConfigurationFailedException;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ItemsSource;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ValueSource;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.ItemsSourceHandler;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.ValueSourceHandler;
import com.tmt.kontroll.ui.page.segments.PageSegment;

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
public abstract class FormControlConfigurator extends PageSegmentConfigurator {

	@Autowired
	ItemsSourceHandler														optionsHandler;

	@Autowired
	ValueSourceHandler												valueSourceHandler;

	private static final String								Data							= "data";
	@SuppressWarnings("serial")
	private final List<String>								notInputControls	= new ArrayList<String>() {
																																{
																																	this.add("label");
																																	this.add("select");
																																}
																															};

	private final Class<? extends Annotation>	annotationType;

	protected FormControlConfigurator(final Class<? extends Annotation> annotationType) {
		this.annotationType = annotationType;
	}

	@Override
	public Class<? extends Annotation> getAnnotationType() {
		return this.annotationType;
	}

	@Override
	public void configure(final PageSegment segment) {
		try {
			final Annotation annotation = segment.getClass().getAnnotation(this.annotationType);
			for (final Method method : this.annotationType.getDeclaredMethods()) {
				if (HtmlTag.class.equals(method.getReturnType())) {
					segment.setTag((HtmlTag) method.invoke(annotation));
				} else if (method.getName().startsWith(Data)) {
					this.handleDataAttribute(segment, method.getName(), (String) method.invoke(annotation));
				} else if (ItemsSource.class.equals(method.getReturnType())) {
					this.optionsHandler.handle((ItemsSource) method.invoke(annotation), segment);
				} else if (ValueSource.class.equals(method.getReturnType())) {
					this.valueSourceHandler.handle((ValueSource) method.invoke(annotation), segment);
				} else {
					segment.getAttributes().put(method.getName(), (String) method.invoke(annotation));
				}
				this.handleTypeAttribute(segment, annotation);
			}
		} catch (final Exception e) {
			throw new PageConfigurationFailedException(e);
		}
	}

	private void handleDataAttribute(final PageSegment segment, final String key, final String value) {
		if (value != null && !value.isEmpty()) {
			segment.getAttributes().put(Data + "-" + key.substring(4), StringUtils.uncapitalize(value));
		}
	}

	private void handleTypeAttribute(final PageSegment segment, final Annotation annotation) {
		final String typeName = annotation.annotationType().getSimpleName().replace("Control", "").toLowerCase();
		if (!this.notInputControls.contains(typeName)) {
			segment.getAttributes().put("type", typeName);
		}
	}
}
