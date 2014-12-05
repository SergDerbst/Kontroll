package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.ResetControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link ResetControl}.
 *
 * @author SergDerbst
 *
 */
public class ResetControlConfigurator extends FormControlConfigurator {

	public ResetControlConfigurator() {
		super(ResetControl.class);
	}
}
