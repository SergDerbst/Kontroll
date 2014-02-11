package com.tmt.kontroll.test.persistence.run.data.preparation.entity;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import javax.persistence.Entity;

import org.junit.Before;
import org.junit.Test;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class EntityInstanceProviderTest {

	public static enum TestEnum {
		Bla, Blubb;
	}

	@Entity
	public static class TestEntitySimpleFields {
		public boolean booleanField;
		public byte byteField;
		public char characterField;
		public double doubleField;
		public float floatField;
		public int integerField;
		public long longField;
		public Locale localeField;
		public short shortField;
		public String stringField;
		public Timestamp timestampField;
		public TestEnum enumField;
	}

	public static class TestEntityStaticFinalFields {
		public static final String staticFinalField = "blubb";
		public static String staticField;
		public final String finalField = "blah";
	}

	public static class TestEntityInherited extends TestEntitySimpleFields{}

	public static class TestEntityCollectionFields {
		public List<String> listStringField;
		public Set<Locale> setLocaleField;
		public SortedSet<Integer> sortedSetIntegerField;
	}

	public static class TestEntityMapFields {
		public Map<String, Integer> mapField;
		public SortedMap<Integer, String> sortedMapField;
		public EnumMap<TestEnum, Double> enumMapField;
	}

	public static class TestEntityArrayFields {
		public String[] stringArrayField;
	}

	public static class TestEntityEntityFields {
		public TestEntitySimpleFields entityField;
		public List<TestEntitySimpleFields> entityListField;
	}

	private final ValueProvisionHandler valueProvisionHandler;
	private final EntityInstanceProvider toTest = EntityInstanceProvider.newInstance();

	private long referenceTimestampValue;

	public EntityInstanceProviderTest() throws Exception {
		this.valueProvisionHandler = PersistenceTestContext.instance().valueProvisionHandler();
	}

	@Before
	public void setUp() throws Exception {
		PersistenceTestContext.instance().referenceAsserter();
		this.valueProvisionHandler.reset();
		this.referenceTimestampValue = ((Timestamp) this.valueProvisionHandler.provide(Timestamp.class)).getTime();
	}

	@Test
	public void testThatEntityIsProvidedWithSimpleValues() throws Exception {
		//when
		TestEntitySimpleFields provided = (TestEntitySimpleFields) this.toTest.provideEntities(TestEntitySimpleFields.class);
		//then
		assertNotNull(provided);
		assertEquals(false, provided.booleanField);
		assertEquals(Byte.parseByte("0"), provided.byteField);
		assertEquals("0".charAt(0), provided.characterField);
		assertEquals(new  Double(0.0), (Double) provided.doubleField);
		assertEquals(new Float((float) 0.0), (Float) provided.floatField);
		assertEquals(0, provided.integerField);
		assertEquals(0, provided.longField);
		assertEquals(Locale.GERMAN, provided.localeField);
		assertEquals((short) 0, provided.shortField);
		assertEquals("0", provided.stringField);
		assertEquals(this.referenceTimestampValue + 1, provided.timestampField.getTime());
		assertEquals(TestEnum.Bla, provided.enumField);
		//when
		provided = (TestEntitySimpleFields) this.toTest.provideEntities(TestEntitySimpleFields.class);
		//then
		assertNotNull(provided);
		assertEquals(true, provided.booleanField);
		assertEquals(Byte.parseByte("1"), provided.byteField);
		assertEquals("1".charAt(0), provided.characterField);
		assertEquals(new  Double(1.0), (Double) provided.doubleField);
		assertEquals(new Float((float) 1.0), (Float) provided.floatField);
		assertEquals(1, provided.integerField);
		assertEquals(1, provided.longField);
		assertEquals(Locale.GERMANY, provided.localeField);
		assertEquals((short) 1, provided.shortField);
		assertEquals("1", provided.stringField);
		assertEquals(this.referenceTimestampValue + 2, provided.timestampField.getTime());
		assertEquals(TestEnum.Blubb, provided.enumField);
	}

	@Test
	public void testThatFieldsOfSuperClassAreHandledProperly() throws Exception {
		//when
		final TestEntitySimpleFields provided = (TestEntitySimpleFields) this.toTest.provideEntities(TestEntityInherited.class);
		//then
		assertNotNull(provided);
		assertEquals(false, provided.booleanField);
		assertEquals(Byte.parseByte("0"), provided.byteField);
		assertEquals("0".charAt(0), provided.characterField);
		assertEquals(new  Double(0.0), (Double) provided.doubleField);
		assertEquals(new Float((float) 0.0), (Float) provided.floatField);
		assertEquals(0, provided.integerField);
		assertEquals(0, provided.longField);
		assertEquals(Locale.GERMAN, provided.localeField);
		assertEquals((short) 0, provided.shortField);
		assertEquals("0", provided.stringField);
		assertEquals(this.referenceTimestampValue + 1, provided.timestampField.getTime());
		assertEquals(TestEnum.Bla, provided.enumField);
	}

	@Test
	public void testThatStaticAndFinalFieldsAreHandledProperly() throws Exception {
		//when
		final TestEntityStaticFinalFields provided = (TestEntityStaticFinalFields) this.toTest.provideEntities(TestEntityStaticFinalFields.class);
		//then
		assertNotNull(provided);
		assertEquals("blubb", TestEntityStaticFinalFields.staticFinalField);
		assertEquals("0", TestEntityStaticFinalFields.staticField);
		assertEquals("blah", provided.finalField);
	}

	@Test
	public void testThatEntityIsProvidedWithCollectionValues() throws Exception {
		//when
		final TestEntityCollectionFields provided = (TestEntityCollectionFields) this.toTest.provideEntities(TestEntityCollectionFields.class);
		//then
		assertNotNull(provided);

		assertNotNull(provided.listStringField);
		assertFalse(provided.listStringField.isEmpty());
		assertEquals(1, provided.listStringField.size());
		assertEquals("0", provided.listStringField.get(0));

		assertNotNull(provided.setLocaleField);
		assertFalse(provided.setLocaleField.isEmpty());
		assertEquals(1, provided.setLocaleField.size());
		assertTrue(provided.setLocaleField.contains(Locale.GERMAN));

		assertNotNull(provided.sortedSetIntegerField);
		assertFalse(provided.sortedSetIntegerField.isEmpty());
		assertEquals(1, provided.sortedSetIntegerField.size());
		assertEquals(new Integer(0), provided.sortedSetIntegerField.first());
	}

	@Test
	public void testThatEntityIsProvidedWithMapValues() throws Exception {
		//when
		final TestEntityMapFields provided = (TestEntityMapFields) this.toTest.provideEntities(TestEntityMapFields.class);
		//then
		assertNotNull(provided);

		assertNotNull(provided.enumMapField);
		assertFalse(provided.enumMapField.isEmpty());
		assertEquals(1, provided.enumMapField.size());
		assertTrue(provided.enumMapField.keySet().contains(TestEnum.Bla));
		assertTrue(provided.enumMapField.values().contains(0.0));

		assertNotNull(provided.mapField);
		assertFalse(provided.mapField.isEmpty());
		assertEquals(1, provided.mapField.size());
		assertTrue(provided.mapField.keySet().contains("0"));
		assertTrue(provided.mapField.values().contains(0));

		assertNotNull(provided.sortedMapField);
		assertFalse(provided.sortedMapField.isEmpty());
		assertEquals(1, provided.sortedMapField.size());
		assertTrue(provided.sortedMapField.keySet().contains(1));
		assertTrue(provided.sortedMapField.values().contains("1"));
	}

	@Test
	public void testThatEntityIsProvidedWithArrayValues() throws Exception {
		//when
		final TestEntityArrayFields provided = (TestEntityArrayFields) this.toTest.provideEntities(TestEntityArrayFields.class);
		//then
		assertNotNull(provided);
		assertNotNull(provided.stringArrayField);
		assertEquals(1, provided.stringArrayField.length);
		assertEquals("0", provided.stringArrayField[0]);
	}

	@Test
	public void testThatEntityIsProvidedWithEntityValues() throws Exception {
		//when
		final TestEntityEntityFields provided = (TestEntityEntityFields) this.toTest.provideEntities(TestEntityEntityFields.class);
		//then
		assertNotNull(provided);

		assertNotNull(provided.entityField);
		assertEquals(false, provided.entityField.booleanField);
		assertEquals(Byte.parseByte("0"), provided.entityField.byteField);
		assertEquals("0".charAt(0), provided.entityField.characterField);
		assertEquals(new  Double(0.0), (Double) provided.entityField.doubleField);
		assertEquals(new Float((float) 0.0), (Float) provided.entityField.floatField);
		assertEquals(0, provided.entityField.integerField);
		assertEquals(0, provided.entityField.longField);
		assertEquals(Locale.GERMAN, provided.entityField.localeField);
		assertEquals((short) 0, provided.entityField.shortField);
		assertEquals("0", provided.entityField.stringField);
		assertEquals(this.referenceTimestampValue + 1, provided.entityField.timestampField.getTime());
		assertEquals(TestEnum.Bla, provided.entityField.enumField);

		assertNotNull(provided.entityListField);
		assertEquals(1, provided.entityListField.size());
		assertEquals(true, provided.entityListField.get(0).booleanField);
		assertEquals(Byte.parseByte("1"), provided.entityListField.get(0).byteField);
		assertEquals("1".charAt(0), provided.entityListField.get(0).characterField);
		assertEquals(new  Double(1.0), (Double) provided.entityListField.get(0).doubleField);
		assertEquals(new Float((float) 1.0), (Float) provided.entityListField.get(0).floatField);
		assertEquals(1, provided.entityListField.get(0).integerField);
		assertEquals(1, provided.entityListField.get(0).longField);
		assertEquals(Locale.GERMANY, provided.entityListField.get(0).localeField);
		assertEquals((short) 1, provided.entityListField.get(0).shortField);
		assertEquals("1", provided.entityListField.get(0).stringField);
		assertEquals(this.referenceTimestampValue + 2, provided.entityListField.get(0).timestampField.getTime());
		assertEquals(TestEnum.Blubb, provided.entityListField.get(0).enumField);
	}

}
