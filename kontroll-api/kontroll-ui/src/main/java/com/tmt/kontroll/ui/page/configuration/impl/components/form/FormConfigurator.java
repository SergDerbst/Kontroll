package com.tmt.kontroll.ui.page.configuration.impl.components.form;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.Form;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.PageEvent;

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
	protected boolean isResponsible(final PageSegment segment) {
		return segment.getClass().isAnnotationPresent(Form.class);
	}

	@Override
	protected void doConfiguration(final PageSegment segment) {
		final Form form = segment.getClass().getAnnotation(Form.class);
		segment.setTag(HtmlTag.Form);
		segment.getAttributes().put("name", form.name());
		final PageEvent event = new PageEvent(EventType.Submit, "submit");
		event.getArguments().put("targetUrl", form.targetUrl());
		event.getArguments().put("targetScope", form.targetScope());
		event.getArguments().put("dtoClass", form.dtoClass());
		segment.getGeneralEvents().put(EventType.Submit, event);
	}
}
