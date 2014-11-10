package com.tmt.kontroll.ui.config;

import java.util.ArrayList;
import java.util.List;

public class UiProperties {

	private final List<String> layoutComponentBasePackages = new ArrayList<String>();
	
	/**
	 * Returns a list of all the base backages containing {@link PageHeader}s, 
	 * {@link PageBody}s, and {@link PageFooter}s.
	 * @return
	 */
	public List<String> basePackages() {
		return layoutComponentBasePackages;
	}
}
