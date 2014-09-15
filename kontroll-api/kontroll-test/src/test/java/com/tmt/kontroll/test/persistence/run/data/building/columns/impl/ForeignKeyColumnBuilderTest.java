package com.tmt.kontroll.test.persistence.run.data.building.columns.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
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
public class ForeignKeyColumnBuilderTest extends TestDataSetColumnBuilderTest {

	private static final String notResponsibleFieldName = "id";
	private static final String responsibleFieldName = "foreignKeyField";

	protected ForeignKeyColumnBuilderTest() throws Exception {
		super(ForeignKeyDummy.class,
		      responsibleFieldName,
		      notResponsibleFieldName,
		      new ForeignKeyColumnBuilder());
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void verifyThatBuildWorks(final Object entity, final Field field, final DataRowBuilder rowBuilder) {
		verifyStatic();
		ColumnSpec.newColumn(responsibleFieldName);
		verify(super.rowBuilder).with(any(ColumnSpec.class), eq(2));
	}

	@Override
	protected void prepareEntity() {
		((ForeignKeyDummy) super.entity).foreignKeyField = new NoForeignKeyDummy();
	}
}
