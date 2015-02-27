package com.tmt.kontroll.content.business.content;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmt.kontroll.content.business.content.data.in.ContentLoadingContext;
import com.tmt.kontroll.content.business.content.utils.ScopedContentItemComparator;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.content.persistence.services.ScopedContentItemDaoService;
import com.tmt.kontroll.content.verification.ContentConditionVerifier;
import com.tmt.kontroll.persistence.exceptions.EntityNotFoundInDatabaseException;
import com.tmt.kontroll.security.persistence.services.UserAccountDaoService;

@Service
@Transactional
public class ScopedContentItemService {

	@Autowired
	ContentConditionVerifier		verifier;
	@Autowired
	UserAccountDaoService				userService;
	@Autowired
	ScopedContentItemDaoService	scopedContentItemDaoService;

	public Set<ScopedContentItem> fetchValidItems(final Scope scope, final ContentLoadingContext context) {
		final Set<ScopedContentItem> contentItems = new TreeSet<>(new ScopedContentItemComparator());
		for (final ScopedContentItem contentItem : scope.getScopedContentItems()) {
			if (contentItem.getCondition() == null || this.verifier.verify(contentItem.getCondition(), context)) {
				contentItems.add(contentItem);
			}
		}
		return contentItems;
	}

	public Set<ScopedContentItem> init(final Scope scope, final String content, final ContentType type) {
		final String contentValue = content.isEmpty() ? scope.getName() : content;
		final Set<ScopedContentItem> scopedContentItems = new HashSet<>();
		ScopedContentItem item = new ScopedContentItem();
		item.setContent(contentValue);
		item.setType(type);
		item.setScopes(new HashSet<Scope>() {
			private static final long	serialVersionUID	= 1L;
			{
				this.add(scope);
			}
		});
		item.setItemNumber("0");
		item.setPreliminary(false);
		item.setLastEdited(this.userService.findByName("Kontroll"));
		final ScopedContentItem contentItem = this.scopedContentItemDaoService.findByContentAndItemNumber(item.getContent(), item.getItemNumber());
		item = contentItem == null ? this.scopedContentItemDaoService.create(item) : contentItem;
		scopedContentItems.add(item);
		return scopedContentItems;
	}

	public void delete(final ScopedContentItem item) {
		this.scopedContentItemDaoService.delete(item.getId());
	}

	public ScopedContentItem add(final ScopedContentItem item) {
		return this.scopedContentItemDaoService.create(item);
	}

	public ScopedContentItem write(final ScopedContentItem item) throws EntityNotFoundInDatabaseException {
		return this.scopedContentItemDaoService.update(item);
	}
}
