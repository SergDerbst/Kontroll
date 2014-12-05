package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.RadioControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link RadioControl}.
 *
 * @author SergDerbst
 *
 */
public class RadioControlConfigurator extends FormControlConfigurator {

	public RadioControlConfigurator() {
		super(RadioControl.class);
	}
}
