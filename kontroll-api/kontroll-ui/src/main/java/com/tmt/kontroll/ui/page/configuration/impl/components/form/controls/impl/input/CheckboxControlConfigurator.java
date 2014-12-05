package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.CheckboxControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link CheckboxControl}.
 *
 * @author SergDerbst
 *
 */
public class CheckboxControlConfigurator extends FormControlConfigurator {

	public CheckboxControlConfigurator() {
		super(CheckboxControl.class);
	}
}
