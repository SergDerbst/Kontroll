package com.tmt.kontroll.content.persistence.entities;

import org.junit.Before;

import com.tmt.kontroll.content.persistence.selections.BooleanOperator;
import com.tmt.kontroll.test.ObjectDataTest;

public class ScopedContentConditionAttributeTest extends ObjectDataTest<ScopedContentConditionAttribute> {

	public ScopedContentConditionAttributeTest() {
		super(new ScopedContentConditionAttribute());
	}

	@Before
	public void setUp() throws Exception {
		this.getFieldByTypeReferenceMap().put(BooleanOperator.class, BooleanOperator.IsEqual);
		this.getFieldByTypeReferenceMap().put(ScopedContentCondition.class, new ScopedContentCondition());
	}
}
