package com.tmt.kontroll.ui.page;

import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.layout.PageLayout;
import com.tmt.kontroll.ui.page.layout.PageLayoutContent;
import com.tmt.kontroll.ui.page.layout.PageLayoutSegment;

@Component
public class PageContentManager {

	TreeMap<PageContentContext, PageLayoutContent> contentMap = new TreeMap<PageContentContext, PageLayoutContent>();
	
	@Autowired
	PageLayout pageLayout;
	
	public void prepareContent() {
		this.prepareContent(this.pageLayout.getHeaderMap());
		this.prepareContent(this.pageLayout.getBodyMap());
		this.prepareContent(this.pageLayout.getFooterMap());
	}
	
	private void prepareContent(final TreeMap<Pattern, ? extends PageLayoutSegment> segmentMap) {
		for (Entry<Pattern, ? extends PageLayoutSegment> entry : segmentMap.entrySet()) {
			this.prepareContent(entry.getKey(), entry.getValue().getScopeName(), entry.getValue().getChildren());
		}
	}
	
	private void prepareContent(final Pattern pattern, final String scopeName, final List<? extends PageLayoutSegment> segments) {
		for (PageLayoutSegment segment : segments) {			
			if (segment instanceof PageLayoutContent) {
				this.contentMap.put(new PageContentContext(pattern, scopeName), (PageLayoutContent) segment);
				continue;
			}
			final String newScopeName = scopeName + "." + segment.getScopeName();
			this.prepareContent(pattern, newScopeName, segment.getChildren());
		}
	}

	public PageLayoutContent fetchPageLayoutContent(String requestContextPath, String scopeName) {
		for (Entry<PageContentContext, PageLayoutContent> entry : this.contentMap.descendingMap().entrySet()) {
			final PageContentContext pageContentContext = entry.getKey();
			if (pageContentContext.getRequestContextPathPattern().matcher(requestContextPath).find() && pageContentContext.getScopeName().equals(scopeName)) {
				return entry.getValue();
			}
		}
		return null;
	}
}