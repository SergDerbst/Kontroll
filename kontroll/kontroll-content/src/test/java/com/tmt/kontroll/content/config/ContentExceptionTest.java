package com.tmt.kontroll.content.config;

import java.util.ArrayList;

import com.tmt.kontroll.test.ExceptionTest;

public class ContentExceptionTest extends ExceptionTest {

	@SuppressWarnings("serial")
	public ContentExceptionTest() {
		super(new ArrayList<String>(){{
			add("com.tmt.kontroll.content");
		}});
	}
}
