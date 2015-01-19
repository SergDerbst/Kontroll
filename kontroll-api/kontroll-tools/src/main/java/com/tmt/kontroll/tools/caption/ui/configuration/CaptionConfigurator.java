package com.tmt.kontroll.tools.caption.ui.configuration;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.caption.CaptionService;
import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * <p>
 * Configures {@link PageSegment}s annotated with {@link Caption}.
 * </p>
 * <p>
 * It checks if there is a caption for the annotation's value present in the data base and if not
 * creates an initial one and sets it as caption of the page segment.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class CaptionConfigurator extends PageSegmentConfigurator {

	@Autowired
	CaptionService	contentService;

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return com.tmt.kontroll.ui.page.configuration.annotations.content.Caption.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		final String captionIdentifier = segment.getClass().getAnnotation(com.tmt.kontroll.ui.page.configuration.annotations.content.Caption.class).value();
		segment.setCaptionIdentifier(captionIdentifier);
		this.contentService.init(captionIdentifier, segment.getScope());
	}
}
