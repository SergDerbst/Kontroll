package com.tmt.kontroll.content.verification.conditions.attributes.values;

import java.util.ArrayList;

import com.tmt.kontroll.test.ExceptionTest;

public class ConditionAttributeValueVerificationExceptionTest extends ExceptionTest {

	@SuppressWarnings("serial")
	public ConditionAttributeValueVerificationExceptionTest() {
		super(new ArrayList<String>(){{
			this.add(this.getClass().getPackage().getName());
		}});
	}
}
