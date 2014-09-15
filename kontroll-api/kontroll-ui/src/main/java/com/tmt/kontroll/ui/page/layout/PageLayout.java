package com.tmt.kontroll.ui.page.layout;

import java.util.TreeMap;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.commons.utils.RegexPatternComparator;
import com.tmt.kontroll.commons.utils.RegexPatternConverter;

@Component
public class PageLayout {

	private final TreeMap<Pattern, PageLayoutHeader> headerMap = new TreeMap<Pattern, PageLayoutHeader>(new RegexPatternComparator());
	private final TreeMap<Pattern, PageLayoutBody> bodyMap = new TreeMap<Pattern, PageLayoutBody>(new RegexPatternComparator());
	private final TreeMap<Pattern, PageLayoutFooter> footerMap = new TreeMap<Pattern, PageLayoutFooter>(new RegexPatternComparator());

	@Autowired
	RegexPatternConverter regexPatternConverter;
	
	public void addHeader(String requestContextPathPattern, PageLayoutHeader header) {
		this.headerMap.put(this.regexPatternConverter.convertToPattern(requestContextPathPattern), header);
	}
	
	public void addBody(String requestContextPathPattern, PageLayoutBody body) {
		this.bodyMap.put(this.regexPatternConverter.convertToPattern(requestContextPathPattern), body);
	}
	
	public void addFooter(String requestContextPathPattern, PageLayoutFooter footer) {
		this.footerMap.put(this.regexPatternConverter.convertToPattern(requestContextPathPattern), footer);
	}
	
	public PageLayoutHeader fetchHeader(String requestContextPathPattern) {
		return this.headerMap.get(this.regexPatternConverter.convertToPattern(requestContextPathPattern));
	}
	
	public PageLayoutBody fetchBody(String requestContextPathPattern) {
		return this.bodyMap.get(this.regexPatternConverter.convertToPattern(requestContextPathPattern));
	}
	
	public PageLayoutFooter fetchFooter(String requestContextPathPattern) {
		return this.footerMap.get(this.regexPatternConverter.convertToPattern(requestContextPathPattern));
	}
	
	public TreeMap<Pattern, PageLayoutHeader> getHeaderMap() {
		return headerMap;
	}
	
	public TreeMap<Pattern, PageLayoutBody> getBodyMap() {
		return bodyMap;
	}
	
	public TreeMap<Pattern, PageLayoutFooter> getFooterMap() {
		return footerMap;
	}
}
