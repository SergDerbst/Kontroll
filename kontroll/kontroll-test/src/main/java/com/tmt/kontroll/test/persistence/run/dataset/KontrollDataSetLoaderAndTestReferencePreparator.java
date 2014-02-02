package com.tmt.kontroll.test.persistence.run.dataset;

import static com.tmt.kontroll.test.persistence.run.assertion.PersistenceEntityReferenceHolder.addReference;
import static com.tmt.kontroll.test.persistence.run.assertion.PersistenceEntityReferenceHolder.clearReferences;
import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.retrieveFieldsForValueProvision;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.builder.ColumnSpec;
import org.dbunit.dataset.builder.DataRowBuilder;
import org.dbunit.dataset.builder.DataSetBuilder;

import com.tmt.kontroll.test.persistence.dao.PersistenceEntityDaoServiceTest;
import com.tmt.kontroll.test.persistence.dao.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.run.assertion.PersistenceEntityReference;

public class KontrollDataSetLoaderAndTestReferencePreparator {

	private static class InstanceHolder {
		public static KontrollDataSetLoaderAndTestReferencePreparator instance = new KontrollDataSetLoaderAndTestReferencePreparator();
	}

	public static KontrollDataSetLoaderAndTestReferencePreparator instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new KontrollDataSetLoaderAndTestReferencePreparator();
		}
		return InstanceHolder.instance;
	}

	EntityInstanceProvider entityInstanceProvider = EntityInstanceProvider.instance();

	public IDataSet loadDataSetAndPrepareTestReference(final Class<? extends PersistenceEntityDaoServiceTest<?,?,?,?>> testClass,
	                                                               final String entityClassName,
	                                                               final int numberOfEntities) throws Exception {
		final List<Object> entities = new ArrayList<>();
		clearReferences();
		for (int i = 0; i < numberOfEntities; i++) {
			final Object entity = this.entityInstanceProvider.provide(Class.forName(entityClassName));
			addReference(new PersistenceEntityReference(entity));
			entities.add(entity);
		}
		return this.buildDataSet(entities);
	}

	private IDataSet buildDataSet(final List<Object> entities) throws Exception {
		final DataSetBuilder builder = new DataSetBuilder();
		for (final Object entity : entities) {
			final DataRowBuilder row = builder.newRow(entity.getClass().getSimpleName());
			for (final Field field : retrieveFieldsForValueProvision(entity.getClass())) {
				field.setAccessible(true);
				row.with(ColumnSpec.newColumn(field.getName()), field.get(entity));
			}
			row.add();
		}
		return builder.build();
	}
}