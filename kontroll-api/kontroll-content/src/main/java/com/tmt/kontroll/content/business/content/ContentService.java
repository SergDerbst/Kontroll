package com.tmt.kontroll.content.business.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.business.content.data.ContentLoadingContext;
import com.tmt.kontroll.content.business.content.data.ContentSavingContext;
import com.tmt.kontroll.content.exceptions.ContentException;
import com.tmt.kontroll.content.exceptions.NoScopeFoundException;
import com.tmt.kontroll.content.parsers.ContentParser;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

@Service
@Transactional
public class ContentService {

	@Autowired
	ScopeService							scopeService;
	@Autowired
	ScopedContentService			scopedContentService;
	@Autowired
	ScopedContentItemService	scopedContentItemService;
	@Autowired
	ContentParser							contentParser;

	public Set<ContentItem> loadContent(final ContentLoadingContext contentDto) throws ContentException {
		final Scope scope = contentDto.getRequestPattern() != null ? this.scopeService.load(contentDto.getScopeName(), contentDto.getRequestPattern().pattern()) : this.scopeService.load(contentDto.getScopeName(), contentDto.getRequestPath());
		if (scope == null) {
			throw NoScopeFoundException.prepare(contentDto.getScopeName(), contentDto.getRequestPath(), contentDto.getRequestPattern());
		}
		final List<ScopedContent> validContent = this.scopedContentService.loadValidContent(scope, contentDto);
		final Set<ScopedContentItem> validItems = this.scopedContentItemService.fetchValidItems(validContent, contentDto);
		return this.contentParser.parse(validItems, contentDto.getScopeName());
	}

	public Set<ContentItem> saveContent(final ContentSavingContext savingContext, final ContentLoadingContext loadingContext) {
		final List<ScopedContent> scopedContents = this.fetchContent(savingContext, loadingContext);
		final Map<Integer, ScopedContentItem> existingItems = this.fetchExistingItems(scopedContents);
		final Set<ScopedContentItem> newItems = this.generateAndSaveNewItems(savingContext, existingItems);
		this.addNewItemsAndSaveContent(scopedContents, newItems);
		this.cleanUpOldItems(existingItems);
		return this.contentParser.parse(newItems, savingContext.getScopeName());
	}

	private void cleanUpOldItems(final Map<Integer, ScopedContentItem> existingItems) {
		for (final ScopedContentItem item : existingItems.values()) {
			if (item.getScopedContents().size() <= 1) {
				this.scopedContentItemService.delete(item);
			}
		}
	}

	private void addNewItemsAndSaveContent(final List<ScopedContent> scopedContents, final Set<ScopedContentItem> newItems) {
		final List<ScopedContentItem> newItemList = new ArrayList<>();
		for (final ScopedContentItem newItem : newItems) {
			newItemList.add(newItem);
		}
		for (final ScopedContent scopedContent : scopedContents) {
			scopedContent.setScopedContentItems(newItemList);
			this.scopedContentService.write(scopedContent);
		}
	}

	private void saveUpdateContentItem(final ScopedContentItem scopedContentItem) {
		if (scopedContentItem.getId() == null) {
			this.scopedContentItemService.add(scopedContentItem);
		} else {
			this.scopedContentItemService.write(scopedContentItem);
		}
	}

	private Set<ScopedContentItem> generateAndSaveNewItems(final ContentSavingContext savingContext, final Map<Integer, ScopedContentItem> existingItems) {
		final Set<ScopedContentItem> newItems = new TreeSet<>();
		for (final ContentItem contentItem : savingContext.getContent()) {
			ScopedContentItem scopedContentItem = existingItems.get(contentItem.getDbId());
			if (scopedContentItem == null) {
				scopedContentItem = new ScopedContentItem();
			}
			scopedContentItem = this.contentParser.parse(contentItem, scopedContentItem);
			this.saveUpdateContentItem(scopedContentItem);
			newItems.add(scopedContentItem);
			existingItems.remove(contentItem.getDbId());
		}
		return newItems;
	}

	private Map<Integer, ScopedContentItem> fetchExistingItems(final List<ScopedContent> scopedContents) {
		final Map<Integer, ScopedContentItem> existingItemsById = new HashMap<>();
		for (final ScopedContent scopedContent : scopedContents) {
			for (final ScopedContentItem scopedContentItem : scopedContent.getScopedContentItems()) {
				existingItemsById.put(scopedContentItem.getId(), scopedContentItem);
			}
		}
		return existingItemsById;
	}

	private List<ScopedContent> fetchContent(final ContentSavingContext savingContext, final ContentLoadingContext loadingContext) {
		final Scope scope = this.scopeService.load(savingContext.getScopeName(), savingContext.getRequestPattern());
		return this.scopedContentService.loadValidContent(scope, loadingContext);
	}
}
