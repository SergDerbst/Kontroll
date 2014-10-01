package com.tmt.kontroll.ui.page.management;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.PageNotFoundException;
import com.tmt.kontroll.ui.page.Page;

@Component
public class PageHolder {

	private final TreeMap<String, Page>	pageMap	= new TreeMap<>();

	public void addPage(final String pattern, final Page page) {
		if (!this.pageMap.containsKey(pattern)) {
			this.pageMap.put(pattern, page);
		}
	}

	public Page fetchPageByPattern(final String pattern) {
		return this.pageMap.get(pattern);
	}

	public Page fetchPageByPath(final String path) throws PageNotFoundException {
		for (final Entry<String, Page> entry : this.pageMap.entrySet()) {
			if (Pattern.compile(entry.getKey()).matcher(path).find()) {
				return entry.getValue();
			}
		}
		throw PageNotFoundException.prepare(path);
	}
}
