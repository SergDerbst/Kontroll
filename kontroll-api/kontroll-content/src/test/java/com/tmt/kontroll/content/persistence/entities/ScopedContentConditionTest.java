package com.tmt.kontroll.content.persistence.entities;

import java.util.ArrayList;

import org.junit.Before;

import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.test.ObjectDataTest;

public class ScopedContentConditionTest extends ObjectDataTest<ScopedContentCondition> {

	public ScopedContentConditionTest() {
		super(new ScopedContentCondition());
	}

	@Before
	public void setUp() throws Exception {
		this.getFieldByTypeReferenceMap().put(ConditionalOperator.class, ConditionalOperator.And);
		this.getFieldByNameReferenceMap().put("childConditions", new ArrayList<ScopedContentCondition>());
		this.getFieldByNameReferenceMap().put("parentConditions", new ArrayList<ScopedContentCondition>());
		this.getFieldByNameReferenceMap().put("scopedContentItems", new ArrayList<ScopedContentItem>());
		this.getFieldByNameReferenceMap().put("scopedContentConditionAttributes", new ArrayList<ScopedContentConditionAttribute>());
	}
}
