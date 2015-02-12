package com.tmt.kontroll.content.business.content.utils;

import java.util.Comparator;

import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

/**
 * Compares two {@link ScopedContentItem}s according to their item number. If the item number is
 * the same, it compares according to their content. This should suffice, because there is a unique
 * constraint on both fields.
 *
 * @author SergDerbst
 *
 */
public class ScopedContentItemComparator implements Comparator<ScopedContentItem> {

	@Override
	public int compare(final ScopedContentItem i1, final ScopedContentItem i2) {
		final int itemNumberCompared = i1.getItemNumber().compareTo(i2.getItemNumber());
		if (itemNumberCompared != 0) {
			return itemNumberCompared;
		}
		final int contentCompared = i1.getContent().compareTo(i2.getContent());
		if (contentCompared != 0) {
			return contentCompared;
		}
		final int preliminaryCompared = Boolean.compare(i1.isPreliminary(), i2.isPreliminary());
		if (preliminaryCompared != 0) {
			return preliminaryCompared;
		}
		final int deletedCompared = Boolean.compare(i1.isDeleted(), i2.isDeleted());
		if (deletedCompared != 0) {
			return deletedCompared;
		}
		return i1.hashCode() - i2.hashCode();
	}
}
