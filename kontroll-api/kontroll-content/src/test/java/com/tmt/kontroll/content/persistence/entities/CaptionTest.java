package com.tmt.kontroll.content.persistence.entities;

import java.sql.Timestamp;
import java.util.Locale;

import org.junit.Before;

import com.tmt.kontroll.test.ObjectDataTest;

public class CaptionTest extends ObjectDataTest<Caption> {

	public CaptionTest() {
		super(new Caption());
	}

	@Before
	public void setUp() throws Exception {
		this.getFieldByTypeReferenceMap().put(Locale.class, Locale.CANADA);
		this.getFieldByTypeReferenceMap().put(Timestamp.class, new Timestamp(System.currentTimeMillis()));
	}
}
