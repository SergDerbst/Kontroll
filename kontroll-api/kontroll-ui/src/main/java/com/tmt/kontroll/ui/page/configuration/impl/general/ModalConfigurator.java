package com.tmt.kontroll.ui.page.configuration.impl.general;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.general.Modal;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.CssConfigurationHandler;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * <p>
 * Configures {@link PageSegment}s annotated with {@link Modal}.
 * </p>
 * <p>
 * During preparation the css class <i>modalDialog</i> will be
 * attached to the generated standard classes of the page segment.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class ModalConfigurator extends PageSegmentConfigurator {

	@Autowired
	CssConfigurationHandler	cssHandler;

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return Modal.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		this.cssHandler.handle(segment, segment.getClass().getAnnotation(Modal.class).cssClass());
		segment.getAttributes().put("hidden", "");
	}
}
