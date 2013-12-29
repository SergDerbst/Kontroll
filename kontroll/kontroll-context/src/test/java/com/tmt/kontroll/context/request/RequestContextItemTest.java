package com.tmt.kontroll.context.request;

import java.util.HashSet;

import com.tmt.kontroll.context.global.GlobalContextDto;
import com.tmt.kontroll.test.ObjectDataTest;

public class RequestContextItemTest extends ObjectDataTest<RequestContextItem> {
	
	public RequestContextItemTest() {
		super(new RequestContextItem(new RequestContextService<RequestContextDto, GlobalContextDto>() {
			@Override
			public RequestContextDto find(GlobalContextDto data) {
				return null;
			}}, new HashSet<String>()));
	}
}
