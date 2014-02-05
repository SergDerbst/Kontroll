package com.tmt.kontroll.test.persistence.run.data.preparation;

import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.IDataSet;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.reference.Reference;
import com.tmt.kontroll.test.persistence.run.data.reference.ReferenceAsserter;

/**
 * Container class to hold and manage data for persistence tests. It holds the following:
 * </p>
 * <ul>
 * <li>a {@link ValueProvisionHandler} instance used to provide values for test data</li>
 * <li>a list of {@link Reference} instances needed to run a test</li>
 * <li>a {@link ReferenceAsserter} instance to perform assertions for the test</li>
 * <li>an {@link IDataSet} representing the data base before a test run</li>
 * <li>an {@link IDataSet} representing the data base after a test run</li>
 * </ul>
 * </p>
 * The test data holder shall be handled as a "semi-singleton", meaning that for each test method there
 * should be one and only one instance of it, but that each {@link TestDataPreparer} or {@link ValueProvider}
 * instance using the test data holder during the preparation phase will all use that very same instance.
 * </br>
 * A new instance can be created and/or fetched using the static {@link #newInstance} method, whereas that
 * same instance can then be accessed it by calling the also static {@link #instance} method.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class TestDataHolder {

	private static class InstanceHolder {
		public static TestDataHolder instance = new TestDataHolder();
	}

	public static TestDataHolder newInstance() {
		InstanceHolder.instance = new TestDataHolder();
		return InstanceHolder.instance;
	}

	public static TestDataHolder instance() {
		return InstanceHolder.instance;
	}

	private final List<Reference> references = new ArrayList<>();

	private ValueProvisionHandler valueProvisionHandler;
	private ReferenceAsserter referenceAsserter;
	private IDataSet dataSetBefore;
	private IDataSet dataSetAfter;

	public ValueProvisionHandler fetchValueProvisionHandler() {
		if (this.valueProvisionHandler == null) {
			this.valueProvisionHandler = new ValueProvisionHandler();
		}
		return this.valueProvisionHandler;
	}

	public void addReference(final Reference reference) {
		this.references.add(reference);
	}

	public ReferenceAsserter getReferenceAsserter() {
		return this.referenceAsserter;
	}

	public void setReferenceAsserter(final ReferenceAsserter referenceAsserter) {
		this.referenceAsserter = referenceAsserter;
	}

	public List<Reference> getReferences() {
		return this.references;
	}

	public IDataSet getDataSetBefore() {
		return this.dataSetBefore;
	}

	public void setDataSetBefore(final IDataSet dataSetBefore) {
		this.dataSetBefore = dataSetBefore;
	}

	public IDataSet getDataSetAfter() {
		return this.dataSetAfter;
	}

	public void setDataSetAfter(final IDataSet dataSetAfter) {
		this.dataSetAfter = dataSetAfter;
	}
}
