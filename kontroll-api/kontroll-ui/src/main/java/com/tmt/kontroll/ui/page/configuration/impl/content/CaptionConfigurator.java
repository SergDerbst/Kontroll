package com.tmt.kontroll.ui.page.configuration.impl.content;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.content.persistence.services.CaptionDaoService;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;

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
	CaptionDaoService	captionDaoService;

	@Override
	protected boolean isResponsible(final PageSegment segment) {
		return segment.getClass().isAnnotationPresent(com.tmt.kontroll.ui.page.configuration.annotations.content.Caption.class);
	}

	@Override
	protected void doConfiguration(final PageSegment segment) {
		final String captionIdentifier = segment.getClass().getAnnotation(com.tmt.kontroll.ui.page.configuration.annotations.content.Caption.class).value();
		Caption caption = this.captionDaoService.findByIdentifierAndLocale(captionIdentifier, Locale.US);
		if (caption == null) {
			caption = new Caption();
			caption.setIdentifier(captionIdentifier);
			caption.setLocale(Locale.US);
			caption.setText(segment.getScope());
			this.captionDaoService.create(caption);
		}
	}
}
