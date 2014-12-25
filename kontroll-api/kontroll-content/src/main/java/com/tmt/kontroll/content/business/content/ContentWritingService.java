package com.tmt.kontroll.content.business.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.services.ScopeDaoService;
import com.tmt.kontroll.content.persistence.services.ScopedContentDaoService;

@Service
public class ContentWritingService {

	@Autowired
	ScopeDaoService					scopeDaoService;
	@Autowired
	ScopedContentDaoService	scopedContentDaoService;

	public void writeContentItem() {

	}
}
