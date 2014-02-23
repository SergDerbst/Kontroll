package com.tmt.kontroll.test.persistence.run.data.preparation.entity;


import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.persistence.OneToOne;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;

public class EntityReferenceComparatorTest {

	@Mock
	private EntityReference entityReference;

	public static class Dummy {
		@OneToOne
		public RelatedDummy relatedDummy;
	}

	public static class RelatedDummy {
		@OneToOne(mappedBy = "relatedDummy")
		public Dummy myDummyOwnsMe;
	}

	private EntityReferenceComparator toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.toTest = new EntityReferenceComparator();
	}

	@Test
	public void testThatSameReferenceReturnsZero() {
		//given
		final EntityReference reference = new EntityReference(new Dummy(), false, false);
		//when
		final int compared = this.toTest.compare(reference, reference);
		//then
		assertTrue(0 == compared);
	}

	@Test
	public void testThatFirstReferenceIsRelatedToSecondReturnsOne() {
		//given
		final Dummy dummy = new Dummy();
		final RelatedDummy relatedDummy = new RelatedDummy();
		dummy.relatedDummy = relatedDummy;
		//when
		final int compared = this.toTest.compare(new EntityReference(dummy, false, false), new EntityReference(relatedDummy, false, false));
		//then
		assertTrue(compared == 1);
	}

	@Test
	public void testThatSecondReferenceIsRelatedToSecondReturnsMinusOne() {
		//given
		final Dummy dummy = new Dummy();
		final RelatedDummy relatedDummy = new RelatedDummy();
		dummy.relatedDummy = relatedDummy;
		//when
		final int compared = this.toTest.compare(new EntityReference(relatedDummy, false, false), new EntityReference(dummy, false, false));
		//then
		assertTrue(compared == -1);
	}

	@Test
	public void testThatUnrelatedReferencesReturnHashCodeValue() {
		//given
		final Dummy dummy = new Dummy();
		final RelatedDummy relatedDummy = new RelatedDummy();
		//when
		final int compared = this.toTest.compare(new EntityReference(relatedDummy, false, false), new EntityReference(dummy, false, false));
		//then
		assertTrue(compared != 0 && compared != 1 && compared != 1);
	}

	@Test
	public void testThatSameEntityReturnsHashCodeValue() {
		//given
		final Dummy dummy = new Dummy();
		//when
		final int compared = this.toTest.compare(new EntityReference(dummy, false, false), new EntityReference(dummy, false, false));
		//then
		assertTrue(compared != 0 && compared != 1 && compared != 1);
	}

	@Test(expected = RuntimeException.class)
	public void testThatExceptionsArePackedIntoRuntimeException() {
		//given
		when(this.entityReference.referenceType()).thenThrow(new Exception());
		//when
		this.toTest.compare(this.entityReference, new EntityReference(new Dummy(), false, false));
	}
}
