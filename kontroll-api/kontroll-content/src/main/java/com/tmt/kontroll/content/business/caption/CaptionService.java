package com.tmt.kontroll.content.business.caption;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.content.persistence.services.CaptionDaoService;

@Service
public class CaptionService {

	@Autowired
	CaptionDaoService	captionDaoService;

	public Caption init(final String captionIdentifier, final String initialValue) {
		Caption caption = this.captionDaoService.findByIdentifierAndLocale(captionIdentifier, Locale.US);
		if (caption == null) {
			caption = new Caption();
			caption.setIdentifier(captionIdentifier);
			caption.setLocale(Locale.US);
			caption.setText(initialValue);
			this.captionDaoService.create(caption);
		}
		return caption;
	}
}
