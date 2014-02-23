package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import static org.mockito.Mockito.verify;

import java.util.TreeSet;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparerTest;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * <b><i>Note:</i></b>
 * </br>
 * If you encounter a <code>java.lang.VerifyError</code> babbling about some inconsistent stackmap frames,
 * please make sure to follow these <a href="http://blog.triona.de/development/jee/how-to-use-powermock-with-java-7.html">instructions</a>.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
public class ExistsTestDataPreparerTest extends TestDataPreparerTest {

	public ExistsTestDataPreparerTest() {
		super(ExistsTestDataPreparer.newInstance(), TestStrategy.Exists);
	}

	@Override
	protected void verifyReferencesAfterTest() {
		verify(super.testDataHolder).addReferences(TestPhase.Setup, super.references);
		verify(super.testDataHolder).addReferences(TestPhase.Running, super.references);
		verify(super.testDataHolder).addReferences(TestPhase.Verification, super.references);
		verify(super.testDataHolder).addReferences(TestPhase.TearDown, new TreeSet<EntityReference>());
	}
}
