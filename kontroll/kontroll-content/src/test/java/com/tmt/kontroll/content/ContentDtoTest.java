package com.tmt.kontroll.content;

import java.util.HashSet;

import com.tmt.kontroll.context.global.GlobalContextDto;
import com.tmt.kontroll.context.request.RequestContextItem;
import com.tmt.kontroll.test.ObjectDataTest;

public class ContentDtoTest extends ObjectDataTest<ContentDto> {

	public ContentDtoTest() {
		super(new ContentDto(new HashSet<RequestContextItem>(), 
                         new GlobalContextDto(){}, 
                         "scopeName"));
	}
}
