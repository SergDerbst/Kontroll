package com.tmt.kontroll.content;

import java.util.ArrayList;
import java.util.HashSet;

import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.context.global.GlobalContextDto;
import com.tmt.kontroll.context.request.RequestContextItem;
import com.tmt.kontroll.test.ObjectDataTest;

public class ContentContextTest extends ObjectDataTest<ContentContext> {

	public ContentContextTest() {
		super(new ContentContext(new ContentDto(new HashSet<RequestContextItem>(),
		                                        new GlobalContextDto(){},
		                                        "requestContextPath",
		"scopeName"),
		new ArrayList<ScopedContentCondition>()));
	}
}
