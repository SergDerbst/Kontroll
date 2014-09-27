package com.tmt.kontroll.persistence.config;

import java.util.ArrayList;
import java.util.List;

public class PersistenceProperties {

	private static final List<String>	daoServiceBasePackages	= new ArrayList<String>();

	public List<String> daoServiceBasePackages() {
		return daoServiceBasePackages;
	}
}
