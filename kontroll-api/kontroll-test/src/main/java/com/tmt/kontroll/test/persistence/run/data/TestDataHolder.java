package com.tmt.kontroll.test.persistence.run.data;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.dbunit.dataset.IDataSet;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.building.TestDataSetBuilder;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityReferenceComparator;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;

/**
 * Container class to hold and manage data for persistence tests. It holds the following:
 * </p>
 * <ul>
 * <li>a set of all {@link EntityReference} instances needed to run a test</li>
 * <li>a map of lists of {@link EntityReference} instances for each {@link TestPhase}</li>
 * <li>an {@link IDataSet} for setting up the database before the test</li>
 * <li>an {@link IDataSet} for verification of the database at the end of the test</li>
 * <li>an {@link IDataSet} for tearing down the database after the test</li>
 * <li>the type of the primary entity to be tested</li>
 * <li>the number of primary entities required by the test according to {@link PersistenceTestConfig}</li>
 * </ul>
 * </p>
 * An entity is considered as primary, when it is of the type of entity that is handled
 * by the dao service to be tested and when it is used by the test during {@link TestPhase#Running},
 * e.g. in order to create, update or delete it. Entities that are of the primary entity type,
 * but not used by the test can be, for example, those that are needed to setup the database
 * before the test in order to fulfill demands on entity relationships.
 * </p>
 * The references lists are organized in a map according to a given {@link TestPhase}. The
 * test data holder offers several methods to fetch references according to a given test
 * phase.
 * </p>
 * Entity references are stored to the set containing all references automatically on creation,
 * unless that is specifically omitted in the constructor, see also {@link EntityReference}.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
public class TestDataHolder {

	private static class InstanceHolder {
		public static TestDataHolder instance;
	}

	public static TestDataHolder newInstance() {
		InstanceHolder.instance = new TestDataHolder();
		return InstanceHolder.instance;
	}

	private final Set<EntityReference> allReferences = new TreeSet<>(new EntityReferenceComparator());
	private final Map<TestPhase, Set<EntityReference>> referencesMap = new EnumMap<>(TestPhase.class);

	private Class<?> primaryEntityType;
	private int numberOfPrimaryEntities;

	private TestDataHolder() {}

	/**
	 * Adds a set of entity references to the references map according to the given test phase.
	 * 
	 * @param testPhase
	 * @param references
	 */
	public void addReferences(final TestPhase testPhase,
	                          final Set<EntityReference> references) {
		if (this.referencesMap.containsKey(testPhase)) {
			this.referencesMap.get(testPhase).addAll(references);
		} else {
			this.referencesMap.put(testPhase, references);
		}
	}

	/**
	 * Updates the value maps of all {@link EntityReference}s. This can be necessary, when
	 * entity values have been changed after their first instantiation.
	 */
	public void updateReferencesValues() {
		for (final Entry<TestPhase, Set<EntityReference>> entry : this.referencesMap.entrySet()) {
			for (final EntityReference reference : entry.getValue()) {
				reference.updateReferenceValueMap();
			}
		}
	}

	public Set<EntityReference> fetchReferences(final TestPhase testPhase) {
		return this.referencesMap.get(testPhase);
	}

	public List<EntityReference> fetchPrimaryTypeReferences(final TestPhase testPhase) {
		final List<EntityReference> list = new ArrayList<>();
		for (final EntityReference reference : this.referencesMap.get(testPhase)) {
			if (reference.referenceType().equals(this.primaryEntityType)) {
				list.add(reference);
			}
		}
		return list;
	}

	public List<EntityReference> fetchPrimaryReferences(final TestPhase testPhase) {
		final List<EntityReference> primaryReferences = new ArrayList<>();
		for (final EntityReference reference : this.referencesMap.get(testPhase)) {
			if (reference.isPrimary()) {
				primaryReferences.add(reference);
			}
		}
		return primaryReferences;
	}

	public List<EntityReference> fetchNonPrimaryReferences(final TestPhase testPhase) {
		final List<EntityReference> primaryReferences = new ArrayList<>();
		for (final EntityReference reference : this.referencesMap.get(testPhase)) {
			if (!reference.isPrimary()) {
				primaryReferences.add(reference);
			}
		}
		return primaryReferences;
	}

	public IDataSet fetchDataSet(final TestPhase testPhase) throws Exception {
		switch (testPhase) {
			case Setup:
				return this.testDataSetBuilder().buildDataSetForSetup();
			case Verification:
				return this.testDataSetBuilder().buildDataSetForVerification();
			case TearDown:
				return this.testDataSetBuilder().buildDataSetForTearDown();
			default:
				throw new RuntimeException("No data set available for: " + testPhase);
		}
	}

	public Set<EntityReference> allReferences() {
		return this.allReferences;
	}

	public Class<?> primaryEntityType() {
		return this.primaryEntityType;
	}

	public void setPrimaryEntityType(final Class<?> primaryEntityType) {
		this.primaryEntityType = primaryEntityType;
	}

	public int numberOfPrimaryEntities() {
		return this.numberOfPrimaryEntities;
	}

	public void setNumberOfPrimaryEntities(final int numberOfPrimaryEntities) {
		this.numberOfPrimaryEntities = numberOfPrimaryEntities;
	}

	protected TestDataSetBuilder testDataSetBuilder() {
		return PersistenceTestContext.instance().testDataSetBuilder();
	}
}
