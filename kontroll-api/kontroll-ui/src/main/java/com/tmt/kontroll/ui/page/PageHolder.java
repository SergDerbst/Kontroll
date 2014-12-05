package com.tmt.kontroll.ui.page;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.PageNotFoundException;
import com.tmt.kontroll.ui.page.segments.PageSegment;

@Component
public class PageHolder {

	private final TreeMap<String, PageSegment>	pageMap	= new TreeMap<>();

	public void addPage(final String pattern, final PageSegment page) {
		if (!this.pageMap.containsKey(pattern)) {
			this.pageMap.put(pattern, page);
		}
	}

	public PageSegment fetchPageByPattern(final String pattern) {
		PageSegment page = this.pageMap.get(pattern);
		if (page == null) {
			for (final Entry<String, PageSegment> entry : this.pageMap.entrySet()) {
				if (Pattern.compile(entry.getKey()).matcher(pattern).find()) {
					page = entry.getValue();
				}
			}
		}
		return page;
	}

	public PageSegment fetchPageByPath(final String path) throws PageNotFoundException {
		for (final Entry<String, PageSegment> entry : this.pageMap.entrySet()) {
			if (Pattern.compile(entry.getKey()).matcher(path).find()) {
				return entry.getValue();
			}
		}
		throw PageNotFoundException.prepare(path);
	}
}
