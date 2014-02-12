package com.tmt.kontroll.test.persistence.run.data;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.dbunit.dataset.IDataSet;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityReferenceComparator;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;

/**
 * Container class to hold and manage data for persistence tests. It holds the following:
 * </p>
 * <ul>
 * <li>a map of lists of {@link EntityReference} instances needed to run a test</li>
 * <li>an {@link IDataSet} for setting up the database before the test</li>
 * <li>an {@link IDataSet} for verification of the database at the end of the test</li>
 * <li>an {@link IDataSet} for tearing down the database after the test</li>
 * </ul>
 * </p>
 * The references lists are organized in a map according to a given {@link TestPhase}. The
 * test data holder offers several methods to fetch references according to a given test
 * phase.
 * </p>
 * 
 * @author Serg Derbst
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

	private IDataSet dataSetForSetup;
	private IDataSet dataSetForVerification;
	private IDataSet dataSetForTearDown;
	private Class<?> primaryEntityType;
	private int numberOfEntities;

	private TestDataHolder() {}

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
			if (reference.getReferenceType().equals(this.primaryEntityType)) {
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

	public IDataSet fetchDataSetForTestPhase(final TestPhase testPhase) {
		switch (testPhase) {
			case Setup:
				return this.dataSetForSetup();
			case Verification:
				return this.dataSetForVerification();
			case TearDown:
				return this.dataSetForTearDown();
			default:
				throw new RuntimeException("No data set available for: " + testPhase);
		}
	}

	public Set<EntityReference> allReferences() {
		return this.allReferences;
	}

	public IDataSet dataSetForSetup() {
		return this.dataSetForSetup;
	}

	public void setDataSetForSetup(final IDataSet dataSetForSetup) {
		this.dataSetForSetup = dataSetForSetup;
	}

	public IDataSet dataSetForVerification() {
		return this.dataSetForVerification;
	}

	public void setDataSetForVerification(final IDataSet dataSetForVerification) {
		this.dataSetForVerification = dataSetForVerification;
	}

	public IDataSet dataSetForTearDown() {
		return this.dataSetForTearDown;
	}

	public void setDataSetForTearDown(final IDataSet dataSetForTearDown) {
		this.dataSetForTearDown = dataSetForTearDown;
	}

	public Class<?> primaryEntityType() {
		return this.primaryEntityType;
	}

	public void setPrimaryEntityType(final Class<?> primaryEntityType) {
		this.primaryEntityType = primaryEntityType;
	}

	public int numberOfEntities() {
		return this.numberOfEntities;
	}

	public void setNumberOfEntities(final int numberOfEntities) {
		this.numberOfEntities = numberOfEntities;
	}
}
