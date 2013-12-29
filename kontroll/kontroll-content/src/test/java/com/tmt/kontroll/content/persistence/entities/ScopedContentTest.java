package com.tmt.kontroll.content.persistence.entities;

import java.util.ArrayList;

import org.junit.Before;

import com.tmt.kontroll.test.ObjectDataTest;

public class ScopedContentTest extends ObjectDataTest<ScopedContent> {

	public ScopedContentTest() {
		super(new ScopedContent());
	}

	@Before
	public void setUp() throws Exception {
		this.getFieldByTypeReferenceMap().put(Scope.class, new Scope());
		this.getFieldByNameReferenceMap().put("scopedContentItems", new ArrayList<ScopedContentItem>());
		this.getFieldByNameReferenceMap().put("conditions", new ArrayList<ScopedContentCondition>());
	}
}
