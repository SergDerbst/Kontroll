package com.tmt.kontroll.test.dao.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tmt.kontroll.test.config.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class EntityInstanceProviderTest {

	public static class TestEntity {
		public boolean booleanField;
		public byte byteField;
		public char characterField;
		public double doubleField;
		public float floatField;
		public int integerField;
		public Locale localeField;
		public long longField;
		public short shortField;
		public String stringField;
		public Timestamp timestampField;
	}

	public static class TestEntityStaticAndFinal {
		public static final String staticFinalField = "staticFinal";
		public final Integer finalField = 1;
		public static String staticField;
	}

	@Autowired
	private EntityInstanceProvider toTest;

	@Before
	public void setUp() {
		this.toTest.getValueFieldProvisionChain().reset();
	}

	@Test
	public void testThatProvisionWorksForNormalFields() {
		//when
		TestEntity provided = (TestEntity) this.toTest.provide(TestEntity.class);
		final long firstTimestampValue = provided.timestampField.getTime();

		//then
		assertTrue(false == provided.booleanField);
		assertTrue(Byte.parseByte("0") == provided.byteField);
		assertTrue("0".charAt(0) == provided.characterField);
		assertTrue(0.0 == provided.doubleField);
		assertTrue((float) 0.0 == provided.floatField);
		assertTrue(0 == provided.integerField);
		assertTrue(Locale.GERMAN.equals(provided.localeField));
		assertTrue(0 == provided.longField);
		assertTrue((short) 0 == provided.shortField);
		assertTrue("0".equals(provided.stringField));
		assertTrue(System.currentTimeMillis() > firstTimestampValue);

		//when
		provided = (TestEntity) this.toTest.provide(TestEntity.class);
		provided = (TestEntity) this.toTest.provide(TestEntity.class);
		provided = (TestEntity) this.toTest.provide(TestEntity.class);

		assertFalse(false == provided.booleanField);
		assertFalse(Byte.parseByte("0") == provided.byteField);
		assertFalse("0".charAt(0) == provided.characterField);
		assertFalse(0.0 == provided.doubleField);
		assertFalse((float) 0.0 == provided.floatField);
		assertFalse(0 == provided.integerField);
		assertFalse(Locale.GERMAN.equals(provided.localeField));
		assertFalse(0 == provided.longField);
		assertFalse((short) 0 == provided.shortField);
		assertFalse("0".equals(provided.stringField));
		assertTrue(firstTimestampValue + 3 == provided.timestampField.getTime());
	}

	@Test
	public void testThatProvisionWorksForStaticAndFinalFields() {
		//when
		final TestEntityStaticAndFinal provided = (TestEntityStaticAndFinal) this.toTest.provide(TestEntityStaticAndFinal.class);

		//then
		assertEquals("staticFinal", TestEntityStaticAndFinal.staticFinalField);
		assertEquals("0", TestEntityStaticAndFinal.staticField);
		assertEquals(new Integer(0), provided.finalField);
	}
}
