package com.tmt.kontroll.content.persistence.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;

import com.tmt.kontroll.test.ObjectDataTest;

public class ScopeTest extends ObjectDataTest<Scope> {

	public ScopeTest() {
		super(new Scope());
	}

	@Before
	public void setUp() throws Exception {
		this.getFieldByTypeReferenceMap().put(Timestamp.class, new Timestamp(System.currentTimeMillis()));
		this.getFieldByTypeReferenceMap().put(Set.class, new HashSet<ScopedContentItem>());
	}
}
