package com.tmt.kontroll.content.business.content;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.business.content.data.ContentOperatingContext;
import com.tmt.kontroll.content.exceptions.NoContentFoundException;
import com.tmt.kontroll.content.exceptions.TooMuchContentFoundException;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.services.ScopedContentDaoService;
import com.tmt.kontroll.content.verification.ContentConditionVerifier;

/**
 * Business service for business operations on {@link ScopedContent}. All methods are cached or invalidate the proper caches.
 *
 * @author SergDerbst
 *
 */
@Service
public class ScopedContentService {

	@Autowired
	ContentConditionVerifier	verifier;
	@Autowired
	ScopedContentDaoService		scopedContentDaoService;

	//TODO caching
	public List<ScopedContent> load(final Scope scope) {
		return this.scopedContentDaoService.findByScope(scope);
	}

	//TODO caching
	public ScopedContent loadValidContent(final Scope scope, final ContentOperatingContext context) {
		final List<ScopedContent> content = this.load(scope);
		final List<ScopedContent> validContent = new ArrayList<ScopedContent>();
		for (final ScopedContent scopedContent : content) {
			final List<ScopedContentCondition> contentConditions = scopedContent.getConditions();
			if (contentConditions.isEmpty()) {
				validContent.add(scopedContent);
			} else {
				for (final ScopedContentCondition contentCondition : contentConditions) {
					if (this.verifier.verify(contentCondition, context)) {
						validContent.add(scopedContent);
					}
				}
			}
		}
		if (validContent.isEmpty()) {
			throw NoContentFoundException.prepare(context);
		}
		if (validContent.size() > 1) {
			throw TooMuchContentFoundException.prepare(context);
		}
		return validContent.get(0);
	}

	//TODO caching
	public ScopedContent init(final Scope scope) {
		final List<ScopedContent> scopedContents = this.scopedContentDaoService.findByScope(scope);
		if (scopedContents.isEmpty()) {
			final ScopedContent scopedContent = new ScopedContent();
			scopedContent.setScope(scope);
			scopedContent.setName(scope.getName());
			this.scopedContentDaoService.create(scopedContent);
			return scopedContent;
		}
		return null;
	}

	//TODO invalidate caching
	public ScopedContent write(final ScopedContent content) {
		return this.scopedContentDaoService.update(content);
	}
}
