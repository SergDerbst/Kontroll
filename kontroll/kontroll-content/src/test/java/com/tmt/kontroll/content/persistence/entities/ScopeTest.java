package com.tmt.kontroll.content.persistence.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import com.tmt.kontroll.test.ObjectDataTest;

public class ScopeTest extends ObjectDataTest<Scope> {

	public ScopeTest() {
		super(new Scope());
	}

	@Before
	public void setUp() throws Exception {
		this.getFieldByTypeReferenceMap().put(Timestamp.class, new Timestamp(System.currentTimeMillis()));
		this.getFieldByTypeReferenceMap().put(List.class, new ArrayList<ScopedContent>());
	}
}
