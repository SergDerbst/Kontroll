package com.tmt.kontroll.content.business.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.business.content.data.ContentLoadingContext;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
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
	public ScopedContent loadValidContent(final Scope scope, final ContentLoadingContext context) {
		final List<ScopedContent> content = this.load(scope);
		return content.get(0);
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
