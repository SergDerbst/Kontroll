package com.tmt.kontroll.test.persistence.run.data.building.columns.impl;

import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isForeignKeyRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveColumnName;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveIdValue;

import java.lang.reflect.Field;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.dbunit.dataset.builder.ColumnSpec;
import org.dbunit.dataset.builder.DataRowBuilder;

import com.tmt.kontroll.test.persistence.run.data.building.columns.TestDataSetColumnBuilder;

/**
 * Handles the building of foreign key columns.
 * </p>
 * It deems itself responsible for building the column, if the given entity field is a relationship field
 * needing a foreign key value, which is the case, when the field is owning a {@link OneToOne} or a
 * {@link ManyToOne} relationship.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class ForeignKeyColumnBuilder extends TestDataSetColumnBuilder {

	@Override
	protected boolean isResponsible(final Field field) throws Exception {
		return isForeignKeyRelationshipField(field);
	}

	@Override
	protected <E> DataRowBuilder doBuild(final E entity, final Field field, final DataRowBuilder row) throws Exception {
		try {
			return row.with(ColumnSpec.newColumn(retrieveColumnName(field)), retrieveIdValue(field.get(entity)));
		} catch (final NullPointerException e) {
			throw e;
		}
	}
}
