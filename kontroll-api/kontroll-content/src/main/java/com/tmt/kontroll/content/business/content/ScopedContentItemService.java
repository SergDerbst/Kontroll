package com.tmt.kontroll.content.business.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.business.content.data.ContentOperatingContext;
import com.tmt.kontroll.content.business.content.utils.ScopedContentItemComparator;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.content.persistence.services.ScopedContentItemDaoService;
import com.tmt.kontroll.content.verification.ContentConditionVerifier;
import com.tmt.kontroll.persistence.exceptions.EntityNotFoundInDatabaseException;
import com.tmt.kontroll.security.persistence.services.UserAccountDaoService;

@Service
public class ScopedContentItemService {

	@Autowired
	ContentConditionVerifier		verifier;
	@Autowired
	UserAccountDaoService				userService;
	@Autowired
	ScopedContentItemDaoService	scopedContentItemDaoService;
	@Autowired
	ScopedContentService				scopedContentService;

	public Set<ScopedContentItem> fetchValidItems(final ScopedContent scopedContent, final ContentOperatingContext context) {
		final Set<ScopedContentItem> contentItems = new TreeSet<>(new ScopedContentItemComparator());
		for (final ScopedContentItem contentItem : scopedContent.getScopedContentItems()) {
			final List<ScopedContentCondition> conditions = contentItem.getConditions();
			if (conditions.isEmpty()) {
				contentItems.add(contentItem);
			} else {
				for (final ScopedContentCondition condition : conditions) {
					if (this.verifier.verify(condition, context)) {
						contentItems.add(contentItem);
					}
				}
			}
		}
		return contentItems;
	}

	public ScopedContentItem init(final ScopedContent scopedContent, final String content, final ContentType type) {
		final String contentValue = content.isEmpty() ? scopedContent.getScope().getName() : content;
		final List<ScopedContent> scopedContents = new ArrayList<>();
		final List<ScopedContentItem> scopedContentItems = new ArrayList<>();
		ScopedContentItem item = new ScopedContentItem();
		scopedContents.add(scopedContent);
		scopedContentItems.add(item);
		item.setContent(contentValue);
		item.setType(type);
		item.setScopedContents(scopedContents);
		item.setItemNumber("0");
		item.setPreliminary(false);
		item.setLastEdited(this.userService.findByName("Kontroll"));
		final ScopedContentItem contentItem = this.scopedContentItemDaoService.findByContentAndItemNumber(item.getContent(), item.getItemNumber());
		if (contentItem == null) {
			item = this.scopedContentItemDaoService.create(item);
		} else {
			item.setId(contentItem.getId());
			item = this.scopedContentItemDaoService.update(contentItem);
		}
		scopedContent.setScopedContentItems(scopedContentItems);
		this.scopedContentService.write(scopedContent);
		return item;
	}

	public ScopedContentItem add(final ScopedContentItem item) {
		return this.scopedContentItemDaoService.create(item);
	}

	public ScopedContentItem write(final ScopedContentItem item) throws EntityNotFoundInDatabaseException {
		return this.scopedContentItemDaoService.update(item);
	}
}
