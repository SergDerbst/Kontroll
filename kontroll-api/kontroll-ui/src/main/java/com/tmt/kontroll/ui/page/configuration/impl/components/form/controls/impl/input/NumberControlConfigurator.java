package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.NumberControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link NumberControl}.
 *
 * @author SergDerbst
 *
 */
public class NumberControlConfigurator extends FormControlConfigurator {

	public NumberControlConfigurator() {
		super(NumberControl.class);
	}
}
