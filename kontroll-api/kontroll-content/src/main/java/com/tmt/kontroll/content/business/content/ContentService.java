package com.tmt.kontroll.content.business.content;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.business.content.data.in.ContentLoadingContext;
import com.tmt.kontroll.content.business.content.data.in.ContentSavingContext;
import com.tmt.kontroll.content.business.content.data.out.EditedScope;
import com.tmt.kontroll.content.exceptions.ContentException;
import com.tmt.kontroll.content.exceptions.NoScopeFoundException;
import com.tmt.kontroll.content.parsers.ContentParser;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

@Service
@Transactional
public class ContentService {

	@Autowired
	ScopeService							scopeService;
	@Autowired
	ScopedContentItemService	scopedContentItemService;
	@Autowired
	ContentParser							contentParser;

	public Set<ContentItem> loadContent(final ContentLoadingContext contentDto) throws ContentException {
		final Scope scope = contentDto.getRequestPattern() != null ? this.scopeService.load(contentDto.getScopeName(), contentDto.getRequestPattern().pattern()) : this.scopeService.load(contentDto.getScopeName(), contentDto.getRequestPath());
		if (scope == null) {
			throw NoScopeFoundException.prepare(contentDto.getScopeName(), contentDto.getRequestPath(), contentDto.getRequestPattern());
		}
		final Set<ScopedContentItem> validItems = this.scopedContentItemService.fetchValidItems(scope, contentDto);
		return this.contentParser.parse(validItems, contentDto.getScopeName());
	}

	public Set<EditedScope> loadContentForEditing(final String scopeName) {
		final Set<EditedScope> editedScopes = new HashSet<>();
		final List<Scope> scopes = this.scopeService.loadAll(scopeName);
		for (final Scope scope : scopes) {
			editedScopes.add(new EditedScope(scope.getName(), scope.getRequestPattern(), this.contentParser.parse(scope.getScopedContentItems(), scope.getName()), null));
		}
		return editedScopes;
	}

	public EditedScope saveContent(final ContentSavingContext savingContext) {
		Scope scope = this.fetchScopeForSaving(savingContext);
		final Map<Integer, ScopedContentItem> items = this.prepareScopedContentItemMap(scope);
		for (final ContentItem contentItem : savingContext.getContent()) {
			final ScopedContentItem scopedContentItem = this.handleContentItem(contentItem, scope);
			items.put(scopedContentItem.getId(), scopedContentItem);
		}
		scope.setScopedContentItems(new HashSet<>(items.values()));
		scope = this.scopeService.write(scope);
		return new EditedScope(scope.getName(), scope.getRequestPattern(), this.contentParser.parse(scope.getScopedContentItems(), scope.getName()), savingContext.getCurrent());
	}

	private ScopedContentItem handleContentItem(final ContentItem contentItem, final Scope scope) {
		ScopedContentItem scopedContentItem = contentItem.getDbId() == null ? new ScopedContentItem() : this.scopedContentItemService.findById(contentItem.getDbId());
		scopedContentItem = this.contentParser.parse(contentItem, scopedContentItem);
		if (contentItem.getDbId() == null) {
			final Set<Scope> scopes = new HashSet<>();
			scopes.add(scope);
			scopedContentItem.setScopes(scopes);
			return this.scopedContentItemService.add(scopedContentItem);
		} else {
			return this.scopedContentItemService.write(scopedContentItem);
		}
	}

	private Map<Integer, ScopedContentItem> prepareScopedContentItemMap(final Scope scope) {
		final Map<Integer, ScopedContentItem> map = new HashMap<>();
		for (final ScopedContentItem item : scope.getScopedContentItems()) {
			map.put(item.getId(), item);
		}
		return map;
	}

	private Scope fetchScopeForSaving(final ContentSavingContext savingContext) {
		final Scope scope = this.scopeService.load(savingContext.getScopeName(), savingContext.getRequestPattern());
		if (scope == null) {
			throw NoScopeFoundException.prepare(savingContext.getScopeName(), savingContext.getRequestPattern());
		}
		return scope;
	}
}
