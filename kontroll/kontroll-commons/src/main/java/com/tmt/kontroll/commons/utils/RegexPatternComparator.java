package com.tmt.kontroll.commons.utils;

import java.util.Comparator;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class RegexPatternComparator implements Comparator<Pattern> {

	@Override
	public int compare(Pattern p1, Pattern p2) {
		return p1.pattern().compareTo(p2.pattern());
	}
}
