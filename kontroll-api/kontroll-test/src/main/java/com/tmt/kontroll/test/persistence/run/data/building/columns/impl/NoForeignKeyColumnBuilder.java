package com.tmt.kontroll.test.persistence.run.data.building.columns.impl;

import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isForeignKeyRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isRelationshipField;

import java.lang.reflect.Field;

import javax.persistence.ManyToMany;

import org.dbunit.dataset.builder.DataRowBuilder;

import com.tmt.kontroll.test.persistence.run.data.building.columns.TestDataSetColumnBuilder;

/**
 * Column builder for the non-owning side of {@link OneToOne} and {@link OneToMany} relationships.
 * It will ommit any column building, since it assumes that the owning side of the relationship
 * will be handled by the {@link ForeignKeyColumnBuilder}.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
public class NoForeignKeyColumnBuilder extends TestDataSetColumnBuilder {

	@Override
	protected boolean isResponsible(final Field field) throws Exception {
		return
		isRelationshipField(field) &&
		!field.isAnnotationPresent(ManyToMany.class) &&
		!isForeignKeyRelationshipField(field);
	}

	@Override
	protected <E> DataRowBuilder doBuild(final E entity, final Field field, final DataRowBuilder row) throws Exception {
		return row;
	}
}