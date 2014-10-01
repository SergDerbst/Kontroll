package com.tmt.kontroll.context.global;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.commons.utils.RegexPatternComparator;
import com.tmt.kontroll.commons.utils.RegexPatternConverter;
import com.tmt.kontroll.context.request.RequestContextDto;
import com.tmt.kontroll.context.request.RequestContextItem;
import com.tmt.kontroll.context.request.RequestContextService;


/**
 * This class represents the global context of the application, which means it holds the request
 * contexts. A request context is identified by a {@link Pattern} matched against the URL paths
 * of incoming requests. If a pattern matches, a set of {@link RequestContextItem} objects will
 * be returned, representing the context of the current request.
 *
 * @author Sergio Weigel
 *
 */
@Component
public class GlobalContext {

	/**
	 * The global map holding all request contexts identified by their given URL path regex patterns.
	 * The keys will be sorted alphabetically by the string representation of the patterns.
	 */
	TreeMap<Pattern, Set<RequestContextItem>> requestContextMap = new TreeMap<Pattern, Set<RequestContextItem>>(new RegexPatternComparator());

	@Autowired
	RegexPatternConverter regexPatternConverter;

	/**
	 * The global data object. It holds all data that has to be available globally in order
	 * to retrieve {@link RequestContextDto}s using the proper {@link RequestContextService}s.
	 */
	GlobalContextDto globalData;

	public void addRequestContextItem(final String patternString, final RequestContextItem requestContextItem) {
		final Pattern pattern = this.regexPatternConverter.convertToPattern(patternString);
		Set<RequestContextItem> context = this.requestContextMap.get(pattern);
		if (context == null) {
			context = new HashSet<RequestContextItem>();
			this.requestContextMap.put(pattern, context);
		}
		context.add(requestContextItem);
	}

	public Set<RequestContextItem> fetchRequestContext(final Pattern pattern) {
		return this.requestContextMap.get(pattern);
	}

	public Set<RequestContextItem> fetchRequestContext(final String requestContextPath) {
		for (final Entry<Pattern, Set<RequestContextItem>> entry : this.requestContextMap.descendingMap().entrySet()) {
			if (entry.getKey().matcher(requestContextPath).find()) {
				return entry.getValue();
			}
		}
		return null;
	}

	public GlobalContextDto getGlobalContextDto() {
		return this.globalData;
	}

	public void setGlobalContextDto(final GlobalContextDto globalData) {
		this.globalData = globalData;
	}
}