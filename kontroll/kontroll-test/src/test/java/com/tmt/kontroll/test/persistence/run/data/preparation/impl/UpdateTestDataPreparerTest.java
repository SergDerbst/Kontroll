package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.TreeSet;

import org.junit.Before;

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
public class UpdateTestDataPreparerTest extends TestDataPreparerTest {

	private EntityReference updatedReference;

	public UpdateTestDataPreparerTest() {
		super(UpdateTestDataPreparer.newInstance(), TestStrategy.Update);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.updatedReference = new EntityReference(new Dummy(), true, false);
		when(super.entityUpdateProvider.provideNewUpdated(any(EntityReference.class))).thenReturn(this.updatedReference);
	}

	@Override
	@SuppressWarnings("serial")
	protected void verifyReferencesAfterTest() {
		verify(super.testDataHolder).addReferences(TestPhase.Setup, super.references);
		final TreeSet<EntityReference> updatedReferences = new TreeSet<EntityReference>(new EntityReferenceComparator()){{
			this.add(UpdateTestDataPreparerTest.this.updatedReference);
		}};
		verify(super.testDataHolder).addReferences(TestPhase.Running, updatedReferences);
		verify(super.testDataHolder).addReferences(TestPhase.Verification, updatedReferences);
		verify(super.testDataHolder).addReferences(TestPhase.TearDown, new TreeSet<EntityReference>());
	}
}