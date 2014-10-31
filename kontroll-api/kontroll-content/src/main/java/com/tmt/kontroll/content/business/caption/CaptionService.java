package com.tmt.kontroll.content.business.caption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.items.impl.CaptionContentItem;
import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.content.persistence.services.CaptionDaoService;

@Component
public class CaptionService {

	@Autowired
	CaptionDaoService	captionDaoService;

	public CaptionContentItem loadCaption(final CaptionDto captionDto) {
		final Caption caption = this.captionDaoService.findByIdentifierAndLocale(captionDto.getIdentifier(), captionDto.getLocale());
		final CaptionContentItem captionContent = new CaptionContentItem();
		captionContent.setContent(caption.getText());
		captionContent.setId(captionDto.getIdentifier());
		return captionContent;
	}
}
