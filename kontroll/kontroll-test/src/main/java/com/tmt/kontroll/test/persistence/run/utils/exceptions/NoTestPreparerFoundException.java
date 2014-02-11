package com.tmt.kontroll.test.persistence.run.utils.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;

@SuppressWarnings("serial")
public class NoTestPreparerFoundException extends ContextedRuntimeException {

	public static NoTestPreparerFoundException prepare(final PersistenceTestConfig config, final String entityClassName) {
		return (NoTestPreparerFoundException) new NoTestPreparerFoundException().addContextValue("test strategy", config.testStrategy()).addContextValue("entity class name", entityClassName);
	}
}
