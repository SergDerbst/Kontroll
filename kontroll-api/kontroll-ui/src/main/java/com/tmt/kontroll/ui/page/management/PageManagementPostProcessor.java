package com.tmt.kontroll.ui.page.management;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.ui.page.segments.PageSegment;

@Component
public class PageManagementPostProcessor {

	public PageSegment process(final PageSegment segment) {
		this.processContent(segment.getContent(), segment.getDomId());
		int ordinal = 0;
		for (final PageSegment child : segment.getTopChildren()) {
			this.process(child);
			child.setOrdinal(ordinal);
			ordinal++;
		}
		for (final PageSegment child : segment.getMainChildren().values()) {
			this.process(child);
			child.setOrdinal(ordinal);
			ordinal++;
		}
		for (final PageSegment child : segment.getBottomChildren()) {
			child.setOrdinal(ordinal);
			ordinal++;
		}
		return segment;
	}

	private void processContent(final List<ContentItem> content, final String parentId) {
		if (content != null) {
			int counter = 0;
			for (final ContentItem item : content) {
				item.setId(parentId + "." + counter);
				this.processContent(item.getChildren(), item.getId());
				counter++;
			}
		}
	}
}
