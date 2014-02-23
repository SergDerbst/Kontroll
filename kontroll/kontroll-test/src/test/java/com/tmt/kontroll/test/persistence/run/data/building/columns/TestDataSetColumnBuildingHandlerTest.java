package com.tmt.kontroll.test.persistence.run.data.building.columns;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import javax.persistence.Column;
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
 * If you encounter a <code>java.lang.VerifyError</code> babbling about some inconsistent stackmap frames,
 * please make sure to follow these <a href="http://blog.triona.de/development/jee/how-to-use-powermock-with-java-7.html">instructions</a>.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ColumnSpec.class})
public class TestDataSetColumnBuildingHandlerTest {

	public static class ForeignKeyOneDummy {
		@Id
		public Integer id = 1;
		@OneToOne
		@Column(name="biggerBetterBroaderAndFaster")
		public ForeignKeyManyDummy foreignKeyManyDummy;
	}
	public static class ForeignKeyManyDummy {
		@Id
		public Integer id = 2;
		@OneToOne(mappedBy="foreignKeyManyDummy")
		public ForeignKeyOneDummy noForeignKeyOneDummy;
	}

	@Mock
	private DataRowBuilder rowBuilder;
	@Mock
	private ColumnSpec<?> columnSpec;

	private ForeignKeyOneDummy foreignKeyOneDummy;
	private ForeignKeyManyDummy foreignKeyManyDummy;

	private TestDataSetColumnBuildingHandler toTest;

	@Before
	@SuppressWarnings({"unchecked"})
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(ColumnSpec.class);
		when(ColumnSpec.newColumn(any(String.class))).thenReturn((ColumnSpec<Object>) this.columnSpec);
		when(this.rowBuilder.with(any(ColumnSpec.class), any())).thenReturn(this.rowBuilder);
		this.prepareDummies();
		this.toTest = TestDataSetColumnBuildingHandler.instance();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testThatBuildWorksForOneToOneForeignKey() throws Exception {
		//when
		final DataRowBuilder built = this.toTest.build(this.foreignKeyOneDummy, ForeignKeyOneDummy.class.getField("foreignKeyManyDummy"), this.rowBuilder);
		//then
		assertEquals(this.rowBuilder, built);
		verifyStatic();
		ColumnSpec.newColumn("biggerBetterBroaderAndFaster");
		verify(this.rowBuilder).with(any(ColumnSpec.class), eq(new Integer(2)));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testThatBuildWorksForOneToOneNoForeignKey() throws Exception {
		//when
		final DataRowBuilder built = this.toTest.build(this.foreignKeyManyDummy, ForeignKeyManyDummy.class.getField("noForeignKeyOneDummy"), this.rowBuilder);
		//then
		assertEquals(this.rowBuilder, built);
		verifyStatic(never());
		ColumnSpec.newColumn("biggerBetterBroaderAndFaster");
		verify(this.rowBuilder, never()).with(any(ColumnSpec.class), any());
	}

	@SuppressWarnings("serial")
	private void prepareDummies() {
		this.foreignKeyOneDummy = new ForeignKeyOneDummy();
		this.foreignKeyManyDummy = new ForeignKeyManyDummy();
		this.foreignKeyOneDummy.foreignKeyManyDummy = this.foreignKeyManyDummy;
		this.foreignKeyManyDummy.noForeignKeyOneDummy = this.foreignKeyOneDummy;
	}
}
