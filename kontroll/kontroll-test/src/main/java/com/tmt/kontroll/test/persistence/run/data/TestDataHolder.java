package com.tmt.kontroll.test.persistence.run.data;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.dbunit.dataset.IDataSet;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;

/**
 * Container class to hold and manage data for persistence tests. It holds the following:
 * </p>
 * <ul>
 * <li>a {@link ValueProvisionHandler} instance used to provide values for test data during preparation phase</li>
 * <li>lists of {@link EntityReference} instances needed to run a test</li>
 * <li>a {@link EntityReferenceAsserter} instance to perform assertions for the test</li>
 * <li>an {@link IDataSet} representing the data base before a test run</li>
 * <li>an {@link IDataSet} representing the data base after a test run</li>
 * </ul>
 * </p>
 * The references lists are organized in a map according to a given {@link TestPhase}.
 * </p>
 * The test data holder shall be handled as a "semi-singleton", meaning that for each test method there
 * should be one and only one instance of it, while each {@link TestDataPreparer} or {@link ValueProvider}
 * will use that very same instance during preparation phase. A new instance can be created and fetched
 * using the static {@link #newInstance} method, whereas the same instance can be accessed by calling
 * the also static {@link #instance} method.
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

	private final Map<TestPhase, List<EntityReference>> referencesMap = new EnumMap<>(TestPhase.class);

	private Class<?> primaryEntityType;
	private IDataSet dataSetForSetup;
	private IDataSet dataSetForVerification;
	private IDataSet dataSetForTearDown;

	private TestDataHolder() {}

	public void addReferences(final TestPhase testPhase,
	                          final List<EntityReference> references) {
		this.referencesMap.put(testPhase, references);
	}

	public List<EntityReference> fetchReferences(final TestPhase testPhase) {
		return this.referencesMap.get(testPhase);
	}

	public List<EntityReference> fetchPrimaryReferences(final TestPhase testPhase) {
		final List<EntityReference> primaryReferences = new ArrayList<>();
		for (final EntityReference reference : this.referencesMap.get(testPhase)) {
			if (reference.getEntity().getClass().equals(this.primaryEntityType)) {
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
}
