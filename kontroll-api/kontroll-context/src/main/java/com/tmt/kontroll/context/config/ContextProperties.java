package com.tmt.kontroll.context.config;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.context.request.RequestContextService;

public class ContextProperties {

	private final List<String> requestContextSerivceBasePackages = new ArrayList<String>();
	
	/**
	 * Returns a list of all the base packages containing {@link RequestContextService}s.
	 * 
	 * @return
	 */
	public List<String> getRequestContextServiceBasePackages() {
		return requestContextSerivceBasePackages;
	}
}
