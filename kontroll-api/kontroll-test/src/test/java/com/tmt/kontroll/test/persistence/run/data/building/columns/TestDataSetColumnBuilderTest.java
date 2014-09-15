package com.tmt.kontroll.test.persistence.run.data.building.columns;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.lang.reflect.Field;

import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.dbunit.dataset.builder.ColumnSpec;
import org.dbunit.dataset.builder.DataRowBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
@RunWith(PowerMockRunner.class)
@PrepareForTest({ColumnSpec.class})
public abstract class TestDataSetColumnBuilderTest {

	public static class ForeignKeyDummy {
		@Id
		public Integer id = 1;
		@OneToOne
		public NoForeignKeyDummy foreignKeyField;
	}
	public static class NoForeignKeyDummy {
		@Id
		public Integer id = 2;
		@OneToOne(mappedBy="foreignKeyField")
		public ForeignKeyDummy noForeignKeyField;
	}

	@Mock
	protected DataRowBuilder rowBuilder;
	@Mock
	private TestDataSetColumnBuilder nextBuilder;

	protected final Object entity;
	protected final Field responsibleField;
	protected final Field notResponsibleField;
	protected final Object expectedNotResponsibleValue;
	protected final TestDataSetColumnBuilder toTest;

	protected TestDataSetColumnBuilderTest(final Class<?> entityClass,
	                                       final String responsibleFieldName,
	                                       final String notResponsibleFieldName,
	                                       final TestDataSetColumnBuilder toTest) throws Exception {
		this.entity = entityClass.newInstance();
		this.responsibleField = this.entity.getClass().getDeclaredField(responsibleFieldName);
		this.notResponsibleField = this.entity.getClass().getDeclaredField(notResponsibleFieldName);
		this.expectedNotResponsibleValue = this.notResponsibleField.get(this.entity);
		this.toTest = toTest;
	}

	protected abstract void verifyThatBuildWorks(final Object entity, final Field field, final DataRowBuilder rowBuilder);

	protected abstract void prepareEntity();

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(ColumnSpec.class);
		this.prepareEntity();
	}

	@Test
	public void testThatBuildWorks() throws Exception {
		//when
		this.toTest.build(this.entity, this.responsibleField, this.rowBuilder);
		//then
		this.verifyThatBuildWorks(this.entity, this.responsibleField, this.rowBuilder);
	}

	@Test
	public void testThatNexBuilderIsCalled() throws Exception {
		//given
		this.toTest.setNextRowBuilder(this.nextBuilder);
		//when
		this.toTest.build(this, this.notResponsibleField, this.rowBuilder);
		//then
		verify(this.nextBuilder).build(this, this.notResponsibleField, this.rowBuilder);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testThatDefaultColumnBuildingIsCalledOnNoNextProvider() throws Exception {
		//given
		this.toTest.setNextRowBuilder(null);
		//when
		this.toTest.build(this.entity, this.notResponsibleField, this.rowBuilder);
		//then
		verifyStatic();
		ColumnSpec.newColumn(this.notResponsibleField.getName());
		verify(this.rowBuilder).with(any(ColumnSpec.class), eq(this.expectedNotResponsibleValue));
	}
}
