package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.responsibility;

import java.lang.reflect.Field;

public interface ValueHandlingResponsibilityClaimer {

	public boolean claimResponsibility(final Field field, final Class<?>... types);
}
