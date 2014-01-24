package com.tmt.kontroll.test.persistence.dao.entity.value.responsibility;

public interface ValueHandlingResponsibilityClaimer {

	public boolean claimResponsibility(final String fieldName, final Class<?>... types);
}
