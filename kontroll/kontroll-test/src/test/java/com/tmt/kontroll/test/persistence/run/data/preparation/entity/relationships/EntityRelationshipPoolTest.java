package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;

public class EntityRelationshipPoolTest {

	@Mock
	private EntityRelationship entityRelationship;
	@Mock
	private EntityReference owningReference;
	@Mock
	private EntityReference relatingReference;
	@Mock
	private EntityReference nonExistingReference;

	private EntityRelationshipPool toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		when(this.entityRelationship.owningEntityReference()).thenReturn(this.owningReference);
		when(this.entityRelationship.relatingEntityReference()).thenReturn(this.relatingReference);
		this.toTest = EntityRelationshipPool.newInstance();
	}

	@Test
	public void testThatAddEntityRelationshipWorks() {
		//when
		this.toTest.addEntityRelationship(this.entityRelationship);
		//then
		assertEquals(new Integer(1), (Integer) this.toTest.size());
	}

	@Test
	public void testThatRetrieveRelationshipByEntityReferenceWorksForAnyReferenceOwning() {
		//given
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByEntityReference(this.owningReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	public void testThatRetrieveRelationshipByEntityReferenceWorksForAnyReferenceRelating() {
		//given
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByEntityReference(this.relatingReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	public void testThatRetrieveRelationshipByEntityReferenceReturnsNullForNonExisting() {
		//given
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByEntityReference(this.nonExistingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceWorksForManyToOneVsManyToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(ManyToOne.class, this.owningReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceWorksForOneToManyVsManyToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(ManyToOne.class, this.owningReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceWorksForManyToOneVsOneToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(OneToMany.class, this.owningReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceWorksForOneToManyVsOneToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(OneToMany.class, this.owningReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceWorksForManyToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(ManyToMany.class, this.owningReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceWorksForOneToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(OneToOne.class, this.owningReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceReturnsNullForNonExisting() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(OneToOne.class, this.nonExistingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceReturnsNullForManyToManyVsOneToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(OneToOne.class, this.owningReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceReturnsNullForManyToManyVsOneToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(OneToMany.class, this.owningReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceReturnsNullForManyToManyVsManyToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(ManyToOne.class, this.owningReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceReturnsNullForOneToOneVsManyToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(ManyToMany.class, this.owningReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceReturnsNullForOneToOneVsOneToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(OneToMany.class, this.owningReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceReturnsNullForOneToOneVsManyToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(ManyToOne.class, this.owningReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceReturnsNullForManyToOneVsOneToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(OneToOne.class, this.owningReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceReturnsNullForManyToOneVsManyToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(ManyToMany.class, this.owningReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceReturnsNullForOneToManyVsOneToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(OneToOne.class, this.owningReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByOwningEntityReferenceReturnsNullForOneToManyVsManyToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByOwningEntityReference(ManyToMany.class, this.owningReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceWorksForManyToOneVsManyToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(ManyToOne.class, this.relatingReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceWorksForOneToManyVsManyToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(ManyToOne.class, this.relatingReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceWorksForManyToOneVsOneToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(OneToMany.class, this.relatingReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceWorksForOneToManyVsOneToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(OneToMany.class, this.relatingReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceWorksForManyToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(ManyToMany.class, this.relatingReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceWorksForOneToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(OneToOne.class, this.relatingReference);
		//then
		assertEquals(this.entityRelationship, retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceReturnsNullForNonExisting() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(OneToOne.class, this.nonExistingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceReturnsNullForManyToManyVsOneToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(OneToOne.class, this.relatingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceReturnsNullForManyToManyVsOneToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(OneToMany.class, this.relatingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceReturnsNullForManyToManyVsManyToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(ManyToOne.class, this.relatingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceReturnsNullForOneToOneVsManyToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(ManyToMany.class, this.relatingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceReturnsNullForOneToOneVsOneToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(OneToMany.class, this.relatingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceReturnsNullForOneToOneVsManyToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(ManyToOne.class, this.relatingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceReturnsNullForManyToOneVsOneToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(OneToOne.class, this.relatingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceReturnsNullForManyToOneVsManyToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) ManyToOne.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(ManyToMany.class, this.relatingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceReturnsNullForOneToManyVsOneToOne() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(OneToOne.class, this.relatingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatRetrieveRelationshipByRelatingEntityReferenceReturnsNullForOneToManyVsManyToMany() {
		//given
		when(this.entityRelationship.relationshipType()).thenReturn((Class) OneToMany.class);
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		final EntityRelationship retrieved = this.toTest.retrieveRelationshipByRelatingEntityReference(ManyToMany.class, this.relatingReference);
		//then
		assertNull(retrieved);
	}

	@Test
	public void testThatClearWorks() {
		//given
		this.toTest.addEntityRelationship(this.entityRelationship);
		//when
		this.toTest.clear();
		//then
		assertEquals(new Integer(0), (Integer) this.toTest.size());
	}
}
