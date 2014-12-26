package com.tmt.kontroll.content.business.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.business.content.data.ContentOperatingContext;
import com.tmt.kontroll.content.exceptions.ContentException;
import com.tmt.kontroll.content.exceptions.NoScopeFoundException;
import com.tmt.kontroll.content.parsers.ContentParser;
import com.tmt.kontroll.content.persistence.entities.Scope;

/**
 * <p>
 * A service to load content according to a set of conditions. The application
 * requests for content with a DTO containing a set of attributes reflecting its
 * current state. The scope of the current state of the application determines where
 * content is to be shown (i.e. in which part of the current view). By this scope the
 * service will be able to determine what conditions and reference values it has to
 * match against the actual values of the application state in order to return the
 * proper content.
 * </p>
 * <p>
 * Any content consists of several content items, which are matched against their own sets
 * of conditions, so that such an item will only appear within the content shown, if
 * its conditions are all true. If no content items match the actual conditions, the
 * content will be empty.
 * </p>
 * <p>
 * Only one content can be present within one scope and at any given set of conditions.
 * </p>
 *
 * @author Sergio Weigel
 *
 * @param <DTO>
 *          - The DTO coming from the application.
 */
@Service
public class ContentService {

	@Autowired
	ScopeService							scopeLoadingService;
	@Autowired
	ScopedContentService			scopedContentService;
	@Autowired
	ScopedContentItemService	scopedContentItemService;
	@Autowired
	ContentParser							scopedContentParser;

	public List<ContentItem> loadContent(final ContentOperatingContext contentDto) throws ContentException {
		final Scope scope = contentDto.getRequestPattern() != null ? this.scopeLoadingService.load(contentDto.getScopeName(), contentDto.getRequestPattern().pattern()) : this.scopeLoadingService.load(contentDto.getScopeName(), contentDto.getRequestPath());
		if (scope == null) {
			throw NoScopeFoundException.prepare(contentDto.getScopeName(), contentDto.getRequestPath(), contentDto.getRequestPattern());
		}
		return this.scopedContentParser.parse(this.scopedContentItemService.fetchValidItems(this.scopedContentService.loadValidContent(scope, contentDto), contentDto), contentDto.getScopeName());
	}
}
