package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.responsibility;

public interface ValueHandlingResponsibilityClaimer {

	public boolean claimResponsibility(final String fieldName, final Class<?>... types);
}
