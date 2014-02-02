package com.tmt.kontroll.test.persistence.run.data;

import org.dbunit.dataset.IDataSet;

public class DataSetHolder {

	private static class InstanceHolder {
		public static DataSetHolder instance = new DataSetHolder();
	}

	public static DataSetHolder instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new DataSetHolder();
		}
		return InstanceHolder.instance;
	}

	private IDataSet dataSetBefore;
	private IDataSet dataSetAfter;

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