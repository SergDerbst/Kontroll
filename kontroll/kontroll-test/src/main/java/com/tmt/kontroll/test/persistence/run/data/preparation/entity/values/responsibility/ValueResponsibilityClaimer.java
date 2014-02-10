package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.responsibility;

import java.lang.reflect.Field;

public interface ValueResponsibilityClaimer {

	public boolean claimResponsibility(final Field field, final Class<?>... types);
}
