package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.responsibility;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

public interface ValueResponsibilityClaimer {

	public boolean claimResponsibility(final ValueProvisionKind kind, final Class<?>... types);
}
