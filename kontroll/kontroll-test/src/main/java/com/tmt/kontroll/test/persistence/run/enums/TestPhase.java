package com.tmt.kontroll.test.persistence.run.enums;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;

/**
 * Represents the phase a persistence test can be in. This is used to identify the right
 * set of references for the right test phase.
 * </p>
 * For more information on the single test phases, read the documentation of the enum values.
 * 
 * @author Serg Derbst
 *
 */
public enum TestPhase {
	/**
	 * No test phase at all. So, actually, it's just a dummy for custom use.
	 */
	None,
	/**
	 * The phase before a test method is run. During this phase all data for the given test is
	 * being prepared. The tests will run based on a database set up at the end of this phase.
	 */
	Setup,
	/**
	 * The phase when the actual tests methods are being run. The references marked under this
	 * phase will be available for use during the tests.
	 */
	Running,
	/**
	 * The phase at the end of a test. During this phase the state of the data base will be
	 * verified according to the set of data that has been prepared for it during preparation.
	 * Verification can be omitted by setting {@link PersistenceTestConfig#omitDbVerification}
	 * to true.
	 */
	Verification,
	/**
	 * The phase after a test method has been run. During this phase, the database will be set
	 * to the data set marked under this phase. Usually this should be an empty set, in order
	 * to assure that tests are repeatable and independent from each other.
	 */
	TearDown;
}
