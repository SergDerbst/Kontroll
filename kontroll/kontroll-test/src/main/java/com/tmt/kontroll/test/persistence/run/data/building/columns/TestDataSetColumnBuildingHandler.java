package com.tmt.kontroll.test.persistence.run.data.building.columns;

import java.lang.reflect.Field;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.builder.DataRowBuilder;

import com.tmt.kontroll.test.persistence.run.data.building.columns.impl.ForeignKeyColumnBuilder;
import com.tmt.kontroll.test.persistence.run.data.building.columns.impl.NoForeignKeyColumnBuilder;

/**
 * Handles the column building for {@link IDataSet}s. If none of the registered {@link TestDataSetColumnBuilder}s
 * have claimed responsibility, it will not throw an exception by default. Instead, it will assume that the column
 * can be built regularily using the {@link DataRowBuilder}. Any exception arosed from that is either caused by a
 * faulty entity implementation or a missing {@link TestDataSetColumnBuilder}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class TestDataSetColumnBuildingHandler {

	private static class InstanceHolder {
		public static TestDataSetColumnBuildingHandler instance;
	}

	public static TestDataSetColumnBuildingHandler instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new TestDataSetColumnBuildingHandler();
		}
		return InstanceHolder.instance;
	}

	private TestDataSetColumnBuilder firstRowBuilder;

	private TestDataSetColumnBuildingHandler() {
		this.setUpRowBuilding();
	}

	public void setUpRowBuilding() {
		this.addRowBuilder(new ForeignKeyColumnBuilder());
		this.addRowBuilder(new NoForeignKeyColumnBuilder());
	}

	public <E> DataRowBuilder build(final E entity,
	                                final Field field,
	                                final DataRowBuilder row) throws Exception {
		return this.firstRowBuilder.build(entity, field, row);
	}

	public void addRowBuilder(final TestDataSetColumnBuilder rowBuilder) {
		rowBuilder.setNextRowBuilder(this.firstRowBuilder);
		this.firstRowBuilder = rowBuilder;
	}
}
