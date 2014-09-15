package com.tmt.kontroll.ui.config;

import java.util.ArrayList;
import java.util.List;

public class UiProperties {

	private final List<String> layoutComponentBasePackages = new ArrayList<String>();
	
	/**
	 * Returns a list of all the base backages containing {@link PageLayoutHeader}s, 
	 * {@link PageLayoutBody}s, and {@link PageLayoutFooter}s.
	 * @return
	 */
	public List<String> getLayoutBasePackages() {
		return layoutComponentBasePackages;
	}
}
