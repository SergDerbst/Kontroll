package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import static org.mockito.Mockito.verify;

import java.util.TreeSet;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparerTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityReferenceComparator;
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
public class DeleteTestDataPreparerTest extends TestDataPreparerTest {

	public static class Dimmy {}

	public DeleteTestDataPreparerTest() {
		super(DeleteTestDataPreparer.newInstance(), TestStrategy.Delete);
	}


	@Override
	@SuppressWarnings("serial")
	protected void verifyReferencesAfterTest() {
		verify(super.testDataHolder).addReferences(TestPhase.Setup, super.references);
		verify(super.testDataHolder).addReferences(TestPhase.Running, super.references);
		verify(super.testDataHolder).addReferences(TestPhase.Verification, new TreeSet<EntityReference>(new EntityReferenceComparator()){{
			this.add(DeleteTestDataPreparerTest.super.nonPrimaryReference);
		}});
		verify(super.testDataHolder).addReferences(TestPhase.TearDown, new TreeSet<EntityReference>());
	}
}
