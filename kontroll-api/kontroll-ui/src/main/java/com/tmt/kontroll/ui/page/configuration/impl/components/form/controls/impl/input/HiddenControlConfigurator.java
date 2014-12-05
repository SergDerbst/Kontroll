package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.HiddenControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link HiddenControl}.
 *
 * @author SergDerbst
 *
 */
public class HiddenControlConfigurator extends FormControlConfigurator {

	public HiddenControlConfigurator() {
		super(HiddenControl.class);
	}
}
