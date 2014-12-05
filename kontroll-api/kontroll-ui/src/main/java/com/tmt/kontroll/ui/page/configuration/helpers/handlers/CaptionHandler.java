package com.tmt.kontroll.ui.page.configuration.helpers.handlers;

import java.lang.annotation.Annotation;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.content.persistence.services.CaptionDaoService;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Handles the caption of the given {@link PageSegment} by looking up if the caption
 * already exists in the database for the identifier, and if not creates the default
 * caption.
 *
 * @author SergDerbst
 *
 */
@Component
public class CaptionHandler {

	@Autowired
	CaptionDaoService	captionDaoService;

	public <A extends Annotation> void handle(final A config, final PageSegment segment, final String captionId, final String scope) {
		String captionIdentifier = captionId;
		if (captionIdentifier.isEmpty()) {
			captionIdentifier = segment.getDomId();
		}
		segment.setCaptionIdentifier(captionIdentifier);
		Caption caption = this.captionDaoService.findByIdentifierAndLocale(captionIdentifier, Locale.US);
		if (caption == null) {
			caption = new Caption();
			caption.setIdentifier(captionIdentifier);
			caption.setLocale(Locale.US);
			caption.setText(scope);
			this.captionDaoService.create(caption);
		}
	}
}