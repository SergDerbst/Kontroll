package com.tmt.kontroll.content.business.content;

import java.util.HashSet;
import java.util.List;
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
			editedScopes.add(new EditedScope(scope.getName(), scope.getRequestPattern(), this.contentParser.parse(scope.getScopedContentItems(), scope.getName())));
		}
		return editedScopes;
	}

	public Set<ContentItem> saveContent(final ContentSavingContext savingContext, final ContentLoadingContext loadingContext) {
		throw new RuntimeException("not implemented yet, Du Arsch!");
	}
}
