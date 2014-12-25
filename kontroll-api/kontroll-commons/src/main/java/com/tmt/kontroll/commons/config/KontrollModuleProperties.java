package com.tmt.kontroll.commons.config;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Abstract base class for all property defining classes of Kontroll modules.
 * </p>
 * <p>
 * All Kontroll modules have one property called <code>basePackages</code>, which contains
 * the base packages to be scanned at context startup for any given kind of annotated class,
 * type or service. The list can be extended as need by the actual app. If further, more
 * specific base packages are needed these can be defined in the implementing module property
 * classes.
 * </p>
 * <p>
 * By default, <code>com.tmt.kontroll</code> is always contained in the base packages list,
 * so that all of the Kontroll framework packages are always scanned by any module.
 * </p>
 *
 * @author SergDerbst
 *
 */
public abstract class KontrollModuleProperties {

	protected static final List<String>	moduleBasePackages	= new ArrayList<>();

	public KontrollModuleProperties() {
		moduleBasePackages.add("com.tmt.kontroll");
	}

	public List<String> basePackages() {
		return moduleBasePackages;
	}
}
