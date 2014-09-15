package com.tmt.kontroll.content.persistence.entities;

import java.util.ArrayList;

import org.junit.Before;

import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.test.ObjectDataTest;

public class ScopedContentItemTest extends ObjectDataTest<ScopedContentItem> {

	public ScopedContentItemTest() {
		super(new ScopedContentItem());
	}

	@Before
	public void setUp() throws Exception {
		this.getFieldByTypeReferenceMap().put(ContentType.class, ContentType.Audio);
		this.getFieldByNameReferenceMap().put("conditions", new ArrayList<ScopedContentCondition>());
		this.getFieldByNameReferenceMap().put("scopedContents", new ArrayList<ScopedContent>());
	}
}
