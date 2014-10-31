package com.tmt.kontroll.commons.enums;

/**
 * Enum to denote the purpose of the environment.
 *
 * @author SergDerbst
 *
 */
public enum EnvironmentPurpose {

	/**
	 * Indicates that the application context is running for the purpose of initial development,
	 * meaning at a phase when there is no prior data or such needed.
	 */
	Dev_Init("dev"),
	/**
	 * Indicates that the application context is running for the purpose of debug development,
	 * meaning that developement happens at a stage where data must already be already present.
	 */
	Dev_Debug("debug"),
	/**
	 * Indicates that the application context is running for the purpose of integration (running
	 * integration tests etc.).
	 */
	Integration("int"),
	/**
	 * Indicates that the application context is running for the purpose of production.
	 */
	Production("prod");

	private String	value;

	private EnvironmentPurpose(final String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
