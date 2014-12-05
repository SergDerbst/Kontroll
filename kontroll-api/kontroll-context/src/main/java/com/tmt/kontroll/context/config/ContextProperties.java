package com.tmt.kontroll.context.config;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;

public class ContextProperties {

	private final List<String> contextSerivceBasePackages = new ArrayList<String>();
	
	/**
	 * Returns a list of all the base packages containing {@link RequestHandlingService}s.
	 * 
	 * @return
	 */
	public List<String> contextServiceBasePackages() {
		return contextSerivceBasePackages;
	}
}
