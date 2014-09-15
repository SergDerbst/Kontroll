package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;

public class EntityRelationshipTest {

	public static class OneDummy {
		@OneToOne
		public OneRelatedDummy relatedDummy;
	}
	public static class OneRelatedDummy {
		@OneToOne(mappedBy = "relatedDummy")
		public OneDummy dummy;
	}
	public static class ManyDummy {
		@ManyToMany
		public List<ManyRelatedDummy> relatedDummies;
	}
	public static class ManyRelatedDummy {
		@ManyToMany(mappedBy = "relatedDummies")
		public List<ManyDummy> dummies;
	}

	@Mock
	private EntityReference owningEntityReference;
	@Mock
	private EntityReference relatingEntityReference;

	private OneDummy oneDummy;
	private OneRelatedDummy oneRelatedDummy;
	private ManyDummy manyDummy;
	private ManyRelatedDummy manyRelatedDummy;

	@Before
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void setUp() throws Exception {
		initMocks(this);
		this.oneDummy = new OneDummy();
		this.oneRelatedDummy = new OneRelatedDummy();
		this.manyDummy = new ManyDummy();
		this.manyRelatedDummy = new ManyRelatedDummy();
		when(this.owningEntityReference.referenceType()).thenReturn((Class) OneDummy.class);
		when(this.relatingEntityReference.referenceType()).thenReturn((Class) OneRelatedDummy.class);
		when(this.owningEntityReference.entity()).thenReturn(this.oneDummy);
		when(this.relatingEntityReference.entity()).thenReturn(this.oneRelatedDummy);
	}

	@Test
	public void testThatConstructorWorksForSimpleField() throws Exception {
		//when
		final EntityRelationship relationship = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		//then
		assertNotNull(relationship);
		assertEquals(OneToOne.class, relationship.relationshipType());
		assertEquals(new Integer(0), (Integer) relationship.index());
		assertEquals(this.owningEntityReference, relationship.owningEntityReference());
		assertEquals(this.relatingEntityReference, relationship.relatingEntityReference());
		assertEquals(this.oneRelatedDummy, this.oneDummy.relatedDummy);
		assertEquals(this.oneDummy, this.oneRelatedDummy.dummy);
	}

	@Test
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void testThatConstructorWorksForCollectionField() throws Exception {
		//given
		when(this.owningEntityReference.referenceType()).thenReturn((Class) ManyDummy.class);
		when(this.relatingEntityReference.referenceType()).thenReturn((Class) ManyRelatedDummy.class);
		when(this.owningEntityReference.entity()).thenReturn(this.manyDummy);
		when(this.relatingEntityReference.entity()).thenReturn(this.manyRelatedDummy);
		//when
		final EntityRelationship relationship = new EntityRelationship(ManyToMany.class, this.owningEntityReference, this.relatingEntityReference, 0);
		//then
		assertNotNull(relationship);
		assertEquals(ManyToMany.class, relationship.relationshipType());
		assertEquals(new Integer(0), (Integer) relationship.index());
		assertEquals(this.owningEntityReference, relationship.owningEntityReference());
		assertEquals(this.relatingEntityReference, relationship.relatingEntityReference());
		assertTrue(List.class.isAssignableFrom(this.manyDummy.relatedDummies.getClass()));
		assertTrue(List.class.isAssignableFrom(this.manyRelatedDummy.dummies.getClass()));
		assertFalse(this.manyDummy.relatedDummies.isEmpty());
		assertFalse(this.manyRelatedDummy.dummies.isEmpty());
		assertEquals(this.manyRelatedDummy, this.manyDummy.relatedDummies.get(0));
		assertEquals(this.manyDummy, this.manyRelatedDummy.dummies.get(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatConstructorThrowsException() throws Exception {
		//when
		new EntityRelationship(null, this.owningEntityReference, this.relatingEntityReference, 0);
	}

	@Test
	public void testThatEqualsReturnsFalseForNull() throws Exception {
		//given
		final EntityRelationship relationship = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		//when
		final boolean equals = relationship.equals(null);
		//then
		assertFalse(equals);
	}

	@Test
	public void testThatEqualsReturnsFalseForOtherThanEntityRelationshipInstance() throws Exception {
		//given
		final EntityRelationship relationship = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		//when
		final boolean equals = relationship.equals("bla");
		//then
		assertFalse(equals);
	}

	@Test
	public void testThatEqualsReturnsFalseForDifferentRelationshipTypes() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		final EntityRelationship relationshipMany = new EntityRelationship(ManyToMany.class, this.owningEntityReference, this.relatingEntityReference, 0);
		//when
		final boolean equals = relationshipOne.equals(relationshipMany);
		//then
		assertFalse(equals);
	}

	@Test
	public void testThatEqualsReturnsFalseForDifferentIndices() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 1);
		//when
		final boolean equals = relationshipOne.equals(relationshipMany);
		//then
		assertFalse(equals);
	}

	@Test
	public void testThatEqualsReturnsFalseForDifferentOwningEntityReferences() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, this.relatingEntityReference, this.owningEntityReference, 0);
		//when
		final boolean equals = relationshipOne.equals(relationshipMany);
		//then
		assertFalse(equals);
	}

	@Test
	public void testThatEqualsReturnsFalseForDifferentRelatingEntityReferences() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, this.owningEntityReference, new EntityReference(new OneRelatedDummy(), false, false), 0);
		//when
		final boolean equals = relationshipOne.equals(relationshipMany);
		//then
		assertFalse(equals);
	}

	@Test
	public void testThatEqualsReturnsTrue() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		//when
		final boolean equals = relationshipOne.equals(relationshipMany);
		//then
		assertTrue(equals);
	}

	@Test
	public void testThatHashCodeWorks() throws Exception {
		//given
		final EntityRelationship relationship = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		//when
		final int hashCode = relationship.hashCode();
		//then
		assertNotNull(hashCode);
	}

	@Test
	public void testThatCompareToReturnsPositiveForDifferentRelationshipTypes() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		final EntityRelationship relationshipMany = new EntityRelationship(ManyToMany.class, this.owningEntityReference, this.relatingEntityReference, 0);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared > 0);
	}

	@Test
	public void testThatCompareToReturnsNegativeForDifferentRelationshipTypes() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(ManyToMany.class, this.owningEntityReference, this.relatingEntityReference, 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared < 0);
	}

	@Test
	public void testThatCompareToReturnsPositiveForDifferentIndices() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 1);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared > 0);
	}

	@Test
	public void testThatCompareToReturnsNegativeForDifferentIndices() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 1);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared < 0);
	}

	@Test
	public void testThatCompareToReturnsDifferenceForSameOwningEntityTypes() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, new EntityReference(new OneDummy(), false, false), new EntityReference(this.oneRelatedDummy, false, false), 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, new EntityReference(new OneDummy(), false, false), new EntityReference(this.oneRelatedDummy, false, false), 0);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared != 0);
	}

	@Test
	public void testThatCompareToReturnsPositiveForDifferentOwningEntityReference() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, new EntityReference(new OneRelatedDummy(), false, false), new EntityReference(this.oneDummy, false, false), 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, new EntityReference(new OneDummy(), false, false), new EntityReference(this.oneRelatedDummy, false, false), 0);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared > 0);
	}

	@Test
	public void testThatCompareToReturnsNegativeForDifferentOwningEntityReference() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, new EntityReference(new OneDummy(), false, false), new EntityReference(this.oneRelatedDummy, false, false), 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, new EntityReference(new OneRelatedDummy(), false, false), new EntityReference(this.oneDummy, false, false), 0);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared < 0);
	}

	@Test
	public void testThatCompareToReturnsNegativeForNullOwningEntityReference() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, new EntityReference(new OneDummy(), false, false), this.relatingEntityReference, 0);
		when(this.owningEntityReference.entity()).thenReturn(null);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared < 0);
	}

	@Test
	public void testThatCompareToReturnsPositiveForNullOwningEntityReference() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, new EntityReference(new OneDummy(), false, false), this.relatingEntityReference, 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		when(this.owningEntityReference.entity()).thenReturn(null);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared > 0);
	}

	@Test
	public void testThatCompareToReturnsDifferenceForSameRelatingEntityTypes() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, new EntityReference(this.oneRelatedDummy, false, false), new EntityReference(new OneDummy(), false, false), 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, new EntityReference(this.oneRelatedDummy, false, false), new EntityReference(new OneDummy(), false, false), 0);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared != 0);
	}

	@Test
	public void testThatCompareToReturnsNegativeForDifferentRelatingEntityReference() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, new EntityReference(this.oneDummy, false, false), new EntityReference(new OneRelatedDummy(), false, false), 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, new EntityReference(this.oneRelatedDummy, false, false), new EntityReference(new OneDummy(), false, false), 0);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared < 0);
	}

	@Test
	public void testThatCompareToReturnsPositiveForDifferentRelatingEntityReference() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, new EntityReference(this.oneRelatedDummy, false, false), new EntityReference(new OneDummy(), false, false), 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, new EntityReference(this.oneDummy, false, false), new EntityReference(new OneRelatedDummy(), false, false), 0);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared > 0);
	}

	@Test
	public void testThatCompareToReturnsNegativeForNullRelatingEntityReference() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, this.owningEntityReference, new EntityReference(new OneRelatedDummy(), false, false), 0);
		when(this.relatingEntityReference.entity()).thenReturn(null);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared < 0);
	}

	@Test
	public void testThatCompareToReturnsPositiveForNullRelatingEntityReference() throws Exception {
		//given
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, this.owningEntityReference, new EntityReference(new OneRelatedDummy(), false, false), 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, this.owningEntityReference, this.relatingEntityReference, 0);
		when(this.relatingEntityReference.entity()).thenReturn(null);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared > 0);
	}

	@Test
	public void testThatCompareToReturnsZero() throws Exception {
		//given
		final OneDummy oneDummy = new OneDummy();
		final OneRelatedDummy oneRelatedDummy = new OneRelatedDummy();
		final EntityRelationship relationshipOne = new EntityRelationship(OneToOne.class, new EntityReference(oneDummy, false, false), new EntityReference(oneRelatedDummy, false, false), 0);
		final EntityRelationship relationshipMany = new EntityRelationship(OneToOne.class, new EntityReference(oneDummy, false, false), new EntityReference(oneRelatedDummy, false, false), 0);
		//when
		final int compared = relationshipOne.compareTo(relationshipMany);
		//then
		assertTrue(compared == 0);
	}
}
