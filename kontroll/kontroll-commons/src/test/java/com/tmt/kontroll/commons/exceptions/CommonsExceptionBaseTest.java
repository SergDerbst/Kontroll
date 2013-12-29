package com.tmt.kontroll.commons.exceptions;

import java.util.ArrayList;

import com.tmt.kontroll.test.ExceptionTest;

public class CommonsExceptionBaseTest extends ExceptionTest {

	@SuppressWarnings("serial")
	public CommonsExceptionBaseTest() {
		super(new ArrayList<String>(){{
			add("com.tmt.kontroll.commons");
		}});
	}
}
