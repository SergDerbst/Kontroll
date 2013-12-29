package com.tmt.kontroll.ui.page;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentDto;
import com.tmt.kontroll.content.ContentService;
import com.tmt.kontroll.content.exceptions.ContentException;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.ui.page.layout.PageLayout;
import com.tmt.kontroll.ui.page.layout.PageLayoutContent;
import com.tmt.kontroll.ui.page.layout.PageLayoutSegment;

@Component
public class PageContentLoader {

	@Autowired
	GlobalContext globalContext; 

	@Autowired
	PageLayout pageLayout;
	
	@Autowired
	PageContentManager pageContentManager;
	
	@Autowired
	ContentService contentService;
	
	public void loadContent(final String patternString) throws ContentException {
		this.loadContent(patternString, this.pageLayout.fetchHeader(patternString).getChildren());
		this.loadContent(patternString, this.pageLayout.fetchBody(patternString).getChildren());
		this.loadContent(patternString, this.pageLayout.fetchFooter(patternString).getChildren());
	}
	
	private void loadContent(final String patternString, final List<PageLayoutSegment> segments) throws ContentException {
		for (final PageLayoutSegment segment : segments) {
			if (segment instanceof PageLayoutContent) {
				@SuppressWarnings("serial")
				Set<String> scopeNames = new HashSet<String>() {{
					add(segment.getScopeName());
				}};
				this.loadContent(patternString, scopeNames);
			}
		}
	}
	
	public void loadContent(final String requestContextPath, final Set<String> scopeNames) throws ContentException {
		for (final String scopeName : scopeNames) {
			PageLayoutContent pageLayoutContent = this.pageContentManager.fetchPageLayoutContent(requestContextPath, scopeName);
			pageLayoutContent.setContent(this.contentService.loadContent(new ContentDto(this.globalContext.fetchRequestContext(requestContextPath), this.globalContext.getGlobalContextDto(), scopeName)));
		}
	}
}
