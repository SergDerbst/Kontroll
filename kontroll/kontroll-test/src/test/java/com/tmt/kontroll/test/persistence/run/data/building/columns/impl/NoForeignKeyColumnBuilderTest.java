package com.tmt.kontroll.test.persistence.run.data.building.columns.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.lang.reflect.Field;

import org.dbunit.dataset.builder.ColumnSpec;
import org.dbunit.dataset.builder.DataRowBuilder;

import com.tmt.kontroll.test.persistence.run.data.building.columns.TestDataSetColumnBuilderTest;

/**
 * <b><i>Note:</i></b>
 * </br>
 * If you encounter a <code>java.lang.VerifyError</code> because of some inconsistent stackmap frames,
 * please make sure to follow these <a href="http://blog.triona.de/development/jee/how-to-use-powermock-with-java-7.html">instructions</a>.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
public class NoForeignKeyColumnBuilderTest extends TestDataSetColumnBuilderTest {

	private static final String notResponsibleFieldName = "id";
	private static final String responsibleFieldName = "noForeignKeyField";

	protected NoForeignKeyColumnBuilderTest() throws Exception {
		super(NoForeignKeyDummy.class,
		      responsibleFieldName,
		      notResponsibleFieldName,
		      new NoForeignKeyColumnBuilder());
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void verifyThatBuildWorks(final Object entity, final Field field, final DataRowBuilder rowBuilder) {
		verifyStatic(never());
		ColumnSpec.newColumn(any(String.class));
		verify(super.rowBuilder, never()).with(any(ColumnSpec.class), any());
	}

	@Override
	protected void prepareEntity() {
		((NoForeignKeyDummy) super.entity).noForeignKeyField = new ForeignKeyDummy();
	}
}
