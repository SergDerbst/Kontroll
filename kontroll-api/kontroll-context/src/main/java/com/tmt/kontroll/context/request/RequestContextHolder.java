package com.tmt.kontroll.context.request;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.commons.utils.regex.RegexPatternComparator;
import com.tmt.kontroll.commons.utils.regex.RegexPatternConverter;

/**
 * <p>
 * The request context holder holds the request contexts. A request context is identified by a
 * {@link Pattern} matched against the URL paths of incoming requests. If a pattern matches, a
 * set of {@link RequestContextItem} objects will be returned, representing the context of the
 * current request.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class RequestContextHolder {

	/**
	 * The global map holding all request contexts identified by their given URL path regex patterns.
	 * The keys will be sorted alphabetically by the string representation of the patterns.
	 */
	TreeMap<Pattern, Set<RequestContextItem>>	requestContextMap	= new TreeMap<Pattern, Set<RequestContextItem>>(new RegexPatternComparator());

	@Autowired
	RegexPatternConverter											regexPatternConverter;

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
		final Set<RequestContextItem> items = new HashSet<>();
		for (final Entry<Pattern, Set<RequestContextItem>> entry : this.requestContextMap.entrySet()) {
			if (entry.getKey().matcher(requestContextPath).find()) {
				items.addAll(entry.getValue());
			}
		}
		return items;
	}
}
