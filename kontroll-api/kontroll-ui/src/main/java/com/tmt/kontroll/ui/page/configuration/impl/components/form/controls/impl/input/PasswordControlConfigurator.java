package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.PasswordControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link PasswordControl}.
 *
 * @author SergDerbst
 *
 */
public class PasswordControlConfigurator extends FormControlConfigurator {

	public PasswordControlConfigurator() {
		super(PasswordControl.class);
	}
}
