package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.RangeControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link RangeControl}.
 *
 * @author SergDerbst
 *
 */
public class RangeControlConfigurator extends FormControlConfigurator {

	public RangeControlConfigurator() {
		super(RangeControl.class);
	}
}
