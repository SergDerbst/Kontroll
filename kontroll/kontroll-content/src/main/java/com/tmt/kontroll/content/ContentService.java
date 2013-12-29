package com.tmt.kontroll.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.exceptions.ContentException;
import com.tmt.kontroll.content.exceptions.NoContentFoundException;
import com.tmt.kontroll.content.exceptions.NoScopeFoundException;
import com.tmt.kontroll.content.items.ContentItem;
import com.tmt.kontroll.content.parsers.ContentParser;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.services.ScopeDaoService;
import com.tmt.kontroll.content.persistence.services.ScopedContentDaoService;

/**
 * A service to load content according to a set of conditions. The application
 * requests for content with a DTO containing a set of attributes reflecting its
 * current state. Part of these attributes will be the scope of the current
 * state of the application (e.g. in which part of the view content is to be shown). 
 * By this scope the service will be able to determine what conditions and reference
 * values it needs to match against the actual ones in order to return proper
 * content.
 * 
 * @author Serg Derbst
 * 
 * @param <DTO>
 *          - The DTO coming from the application.
 */
@Service
public class ContentService {

	@Autowired
	ContentVerifier verifier;

	@Autowired
	ScopeDaoService scopeDaoService;

	@Autowired
	ScopedContentDaoService scopedContentDaoService;

	@Autowired
	ContentParser scopedContentParser;

	public List<ContentItem<? extends Enum<?>>> loadContent(ContentDto contentDTO) throws ContentException {
		final String contentName = contentDTO.getScopeName();
		final String scopeName = contentName.split("\\.")[0];
		final String scopedContentName = contentName.substring(scopeName.length());
		final Scope scope = this.scopeDaoService.findByName(scopeName);
		if (scope == null) {
			throw NoScopeFoundException.prepare(scopeName);
		}
		final List<ScopedContent> scopedContents = this.scopedContentDaoService.findByScopeAndName(scope, scopedContentName);

		for (ScopedContent scopedContent : scopedContents) {
			final ContentContext context = new ContentContext(contentDTO, scopedContent.getConditions());
			if (this.verifier.verify(context)) {
				return this.scopedContentParser.parse(scopedContent);
			}
		}
		throw NoContentFoundException.prepare(contentDTO);
	}
}
