package com.tmt.kontroll.content.business.content;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.exceptions.ContentException;
import com.tmt.kontroll.content.exceptions.NoContentFoundException;
import com.tmt.kontroll.content.exceptions.NoContentParserFoundException;
import com.tmt.kontroll.content.exceptions.NoScopeFoundException;
import com.tmt.kontroll.content.exceptions.TooMuchContentFoundException;
import com.tmt.kontroll.content.parsers.ContentParser;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.content.persistence.services.ScopeDaoService;
import com.tmt.kontroll.content.persistence.services.ScopedContentDaoService;
import com.tmt.kontroll.content.verification.ContentConditionVerifier;

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
	ContentConditionVerifier	verifier;
	@Autowired
	ScopeDaoService						scopeDaoService;
	@Autowired
	ScopedContentDaoService		scopedContentDaoService;
	@Autowired
	ContentParser							scopedContentParser;

	public List<ContentItem> loadContent(final ContentDto contentDto) throws ContentException {
		final String scopeName = contentDto.getScopeName();
		final String requestContextPath = contentDto.getRequestContextPath();
		final Scope scope = this.scopeDaoService.findByNameAndRequestContextPath(scopeName, requestContextPath);
		if (scope == null) {
			throw NoScopeFoundException.prepare(scopeName, requestContextPath);
		}
		final List<ScopedContent> scopedContents = this.scopedContentDaoService.findByScope(scope);
		return this.verifyAndParseContent(scopedContents, contentDto);
	}

	private List<ContentItem> verifyAndParseContent(final List<ScopedContent> scopedContents, final ContentDto contentDto) throws ContentException {
		final List<ScopedContent> foundContent = this.findValidContent(scopedContents, contentDto);
		if (foundContent.isEmpty()) {
			throw NoContentFoundException.prepare(contentDto);
		}
		if (foundContent.size() > 1) {
			throw TooMuchContentFoundException.prepare(contentDto);
		}
		return this.verifyAndParseContentItems(foundContent.get(0), contentDto);
	}

	private List<ScopedContent> findValidContent(final List<ScopedContent> scopedContents, final ContentDto contentDto) {
		final List<ScopedContent> foundContent = new ArrayList<ScopedContent>();
		for (final ScopedContent scopedContent : scopedContents) {
			final List<ScopedContentCondition> contentConditions = scopedContent.getConditions();
			if (contentConditions.isEmpty()) {
				foundContent.add(scopedContent);
			} else {
				for (final ScopedContentCondition contentCondition : contentConditions) {
					if (this.verifier.verify(contentCondition, contentDto)) {
						foundContent.add(scopedContent);
					}
				}
			}
		}
		return foundContent;
	}

	private List<ContentItem> verifyAndParseContentItems(final ScopedContent scopedContent, final ContentDto contentDto) throws NoContentParserFoundException {
		final List<ScopedContentItem> contentItems = new ArrayList<ScopedContentItem>();
		for (final ScopedContentItem contentItem : scopedContent.getScopedContentItems()) {
			final List<ScopedContentCondition> conditions = contentItem.getConditions();
			if (conditions.isEmpty()) {
				contentItems.add(contentItem);
			} else {
				for (final ScopedContentCondition condition : conditions) {
					if (this.verifier.verify(condition, contentDto)) {
						contentItems.add(contentItem);
					}
				}
			}
		}
		return this.scopedContentParser.parse(contentItems, contentDto.getScopeName());
	}
}
