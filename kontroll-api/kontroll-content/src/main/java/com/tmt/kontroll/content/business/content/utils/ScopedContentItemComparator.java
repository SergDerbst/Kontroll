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
			return i1.getContent().compareTo(i2.getContent());
		}
		return itemNumberCompared;
	}
}
