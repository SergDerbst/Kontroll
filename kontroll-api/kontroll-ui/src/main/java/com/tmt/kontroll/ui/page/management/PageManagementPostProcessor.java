package com.tmt.kontroll.ui.page.management;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.ui.page.segments.PageSegment;
import com.tmt.kontroll.ui.page.segments.PageSegmentChildrenAndContentAccessor;

@Component
public class PageManagementPostProcessor {

	@Autowired
	PageSegmentChildrenAndContentAccessor	childrendAndContentAccessor;

	public PageSegment process(final PageSegment segment) {
		this.processContent(this.childrendAndContentAccessor.fetchContent(segment), segment.getDomId());
		int ordinal = 0;
		for (final PageSegment child : this.childrendAndContentAccessor.fetchTopChildren(segment)) {
			this.process(child);
			child.setOrdinal(ordinal);
			ordinal++;
		}
		for (final PageSegment child : this.childrendAndContentAccessor.fetchMainChildren(segment).values()) {
			this.process(child);
			child.setOrdinal(ordinal);
			ordinal++;
		}
		for (final PageSegment child : this.childrendAndContentAccessor.fetchBottomChildren(segment)) {
			child.setOrdinal(ordinal);
			ordinal++;
		}
		return segment;
	}

	private void processContent(final Set<ContentItem> content, final String parentId) {
		if (content != null) {
			int counter = 0;
			for (final ContentItem item : content) {
				item.setDomId(parentId + "." + counter);
				this.processContent(item.getChildren(), item.getDomId());
				counter++;
			}
		}
	}
}
