package com.tmt.kontroll.ui.page.segments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.ui.page.configuration.annotations.components.containers.Scrollable;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentOrdinalKey;

/**
 * Handles the access to {@link PageSegment} content and children. Children and content should always
 * be accessed using this component, as it will handle special access to children and content if necessary,
 * e.g. on page segments annotated with {@link Scrollable}.
 *
 * @author SergDerbst
 *
 */
@Component
public class PageSegmentChildrenAndContentAccessor {

	@Autowired
	PageSegmentHolder	pageSegmentHolder;

	/**
	 * Adds the given child to the main children of the given parent, taking into account
	 * extra handling such as segments annotated as {@link Scrollable}.
	 *
	 * @param parent
	 * @param child
	 */
	public void addChild(final PageSegment parent, final PageSegment child) {
		if (child != null && !this.containsChild(parent, child)) {
			this.fetchMainChildren(parent).put(new PageSegmentOrdinalKey(child.getOrdinal(), child.getDomId()), child);
		}
	}

	/**
	 * Adds top and bottom children according to the given {@link ChildPosition}. The given
	 * child is also added to the {@link PageSegmentHolder}.
	 *
	 * @param position
	 * @param parent
	 * @param child
	 */
	public void addChild(final ChildPosition position, final PageSegment parent, final PageSegment child) {
		if (child != null && !this.containsChild(parent, child)) {
			if (ChildPosition.Top == position) {
				this.fetchTopChildren(parent).add(child);
			}
			if (ChildPosition.Bottom == position) {
				this.fetchBottomChildren(parent).add(child);
			}
		}
		this.pageSegmentHolder.addPageSegment(child.getDomId(), child);
	}

	public List<PageSegment> fetchTopChildren(final PageSegment segment) {
		return segment.getTopChildren();
	}

	public List<PageSegment> fetchBottomChildren(final PageSegment segment) {
		return segment.getBottomChildren();
	}

	public Map<PageSegmentOrdinalKey, PageSegment> fetchMainChildren(final PageSegment segment) {
		if (segment.getClass().isAnnotationPresent(Scrollable.class)) {
			return segment.getMainChildren().get(new PageSegmentOrdinalKey(0, segment.getDomId() + ".scroller")).getMainChildren();
		}
		return segment.getMainChildren();
	}

	public List<PageSegment> fetchAllChildren(final PageSegment segment) {
		final List<PageSegment> children = new ArrayList<>();
		children.addAll(this.fetchTopChildren(segment));
		children.addAll(this.fetchMainChildren(segment).values());
		children.addAll(this.fetchBottomChildren(segment));
		return children;
	}

	private boolean containsChild(final PageSegment parent, final PageSegment child) {
		if (parent.containsChild(child)) {
			return true;
		} else if (parent.getClass().isAnnotationPresent(Scrollable.class)) {
			final PageSegment realParent = parent.getMainChildren().get(new PageSegmentOrdinalKey(0, parent.getDomId() + ".scroller"));
			return realParent.containsChild(child);
		} else {
			return false;
		}
	}

	/**
	 * Adds the given content to the given page segment, taking into account extra handling
	 * of such segments annotated with {@link Scrollable}.
	 *
	 * @param segment
	 * @param content
	 */
	public void addContent(final PageSegment segment, final Set<ContentItem> content) {
		if (segment.getClass().isAnnotationPresent(Scrollable.class)) {
			final PageSegment realParent = segment.getMainChildren().get(new PageSegmentOrdinalKey(0, segment.getDomId() + ".scroller"));
			realParent.setContent(content);
		} else {
			segment.setContent(content);
		}
	}

	/**
	 * Returns the content of the given segment, taking into account extra handling of such
	 * as segments annotated with {@link Scrollable}.
	 *
	 * @param content
	 * @return
	 */
	public Set<ContentItem> fetchContent(final PageSegment segment) {
		if (segment.getClass().isAnnotationPresent(Scrollable.class)) {
			final PageSegment realParent = segment.getMainChildren().get(new PageSegmentOrdinalKey(0, segment.getDomId() + ".scroller"));
			return realParent.getContent();
		} else {
			return segment.getContent();
		}
	}
}
