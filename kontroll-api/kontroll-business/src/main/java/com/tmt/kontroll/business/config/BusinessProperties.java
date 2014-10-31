package com.tmt.kontroll.business.config;

import java.util.ArrayList;
import java.util.List;

public class BusinessProperties {

	private static final List<String>	businessEntityBasePackages	= new ArrayList<String>();

	public List<String> businessEntityBasePackages() {
		return businessEntityBasePackages;
	}
}
