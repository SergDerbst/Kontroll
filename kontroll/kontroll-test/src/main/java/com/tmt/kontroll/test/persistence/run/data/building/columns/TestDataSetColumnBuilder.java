package com.tmt.kontroll.test.persistence.run.data.building.columns;

import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveColumnName;

import java.lang.reflect.Field;

import org.dbunit.dataset.builder.ColumnSpec;
import org.dbunit.dataset.builder.DataRowBuilder;

public abstract class TestDataSetColumnBuilder {

	private TestDataSetColumnBuilder nextRowBuilder;

	protected abstract boolean isResponsible(final Field field) throws Exception;

	protected abstract <E> DataRowBuilder doBuild(final E entity,
	                                              final Field field,
	                                              final DataRowBuilder row) throws Exception;

	public <E> DataRowBuilder build(final E entity,
	                                final Field field,
	                                final DataRowBuilder row) throws Exception {
		field.setAccessible(true);
		if (this.isResponsible(field)) {
			return this.doBuild(entity, field, row);
		}
		if (this.nextRowBuilder == null) {
			return row.with(ColumnSpec.newColumn(retrieveColumnName(field)), field.get(entity));
		}
		return this.nextRowBuilder.build(entity, field, row);
	}

	public void setNextRowBuilder(final TestDataSetColumnBuilder nextRowBuilder) {
		this.nextRowBuilder = nextRowBuilder;
	}
}
