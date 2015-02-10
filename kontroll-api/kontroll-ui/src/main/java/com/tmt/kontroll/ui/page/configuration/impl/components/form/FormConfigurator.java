package com.tmt.kontroll.ui.page.configuration.impl.components.form;

import java.lang.annotation.Annotation;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.Form;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * <p>
 * Configures {@link PageSegment}s annotated with {@link Form}.
 * </p>
 * <p>
 * It does the following:
 * <ul>
 * 	<li>set the page segment's tag to {@link HtmlTag#Form}</li>
 * 	<li>set the page segment's <code>name</code> attribute to {@link Form#name}</li>
 * 	<li>
 * 	create a {@link PageEvent} of type {@link EventType#Submit} with handler name <code>submit</code>
 * 	and its arguments set to the values configured in the form annotation.
 * </li>
 * </ul>
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class FormConfigurator extends PageSegmentConfigurator {

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return Form.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		final Form form = segment.getClass().getAnnotation(Form.class);
		segment.setTag(HtmlTag.Form);
		segment.getAttributes().put("name", form.name());
		segment.getAttributes().put("dtoClass", form.dtoClass().getName());
		final PageEvent event = new PageEvent(EventType.Submit, form.submitHandlers());
		event.getArguments().put("targetUrl", form.targetUrl());
		event.getArguments().put("targetScope", form.targetScope());
		event.getArguments().put("dtoClass", form.dtoClass());
		segment.getGeneralEvents().put(EventType.Submit, event);
	}
}
