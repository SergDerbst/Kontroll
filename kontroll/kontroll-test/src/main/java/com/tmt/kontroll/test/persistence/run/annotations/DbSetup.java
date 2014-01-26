package com.tmt.kontroll.test.persistence.run.annotations;

import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

public @interface DbSetup {

	TestStrategy testStrategy();
}
