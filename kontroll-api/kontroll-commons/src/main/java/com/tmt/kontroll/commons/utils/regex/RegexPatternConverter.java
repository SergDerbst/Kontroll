package com.tmt.kontroll.commons.utils.regex;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class RegexPatternConverter {

	public Pattern convertToPattern(final String patternString) {
		return Pattern.compile(patternString);
	}
	
	public String convertToString(final Pattern pattern) {
		return pattern.pattern();
	}
}
