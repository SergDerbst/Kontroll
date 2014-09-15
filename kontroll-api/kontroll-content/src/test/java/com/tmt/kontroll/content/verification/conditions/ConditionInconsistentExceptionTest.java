package com.tmt.kontroll.content.verification.conditions;

import java.util.ArrayList;

import com.tmt.kontroll.test.ExceptionTest;

public class ConditionInconsistentExceptionTest extends ExceptionTest {

	@SuppressWarnings("serial")
	public ConditionInconsistentExceptionTest() {
		super(new ArrayList<String>(){{
			add(this.getClass().getPackage().getName());
		}});
	}
}
