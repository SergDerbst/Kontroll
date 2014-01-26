package com.tmt.kontroll.test.persistence.run.dataset;

import static com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueTypeHelper.retrieveFields;

import java.lang.reflect.Field;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.builder.ColumnSpec;
import org.dbunit.dataset.builder.DataRowBuilder;
import org.dbunit.dataset.builder.DataSetBuilder;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import com.tmt.kontroll.test.persistence.dao.entity.EntityInstanceProvider;

public class KontrollDataSetLoader extends AbstractDataSetLoader {

	EntityInstanceProvider entityInstanceProvider = EntityInstanceProvider.instance();

	@Override
	public IDataSet loadDataSet(final Class<?> testClass, final String entityClassName) throws Exception {
		return this.buildDataSet(new DataSetBuilder(), this.entityInstanceProvider.provide(Class.forName(entityClassName)));
	}

	private IDataSet buildDataSet(final DataSetBuilder builder, final Object entity) throws Exception {
		final DataRowBuilder row = builder.newRow(entity.getClass().getSimpleName());
		for (final Field field : retrieveFields(entity.getClass())) {
			field.setAccessible(true);
			row.with(ColumnSpec.newColumn(field.getName()), field.get(entity)).add();
		}
		return builder.build();
	}

	@Override
	protected IDataSet createDataSet(final Resource resource) throws Exception {
		return null;
	}
}