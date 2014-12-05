package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.WeekControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link WeekControl}.
 *
 * @author SergDerbst
 *
 */
public class WeekControlConfigurator extends FormControlConfigurator {

	public WeekControlConfigurator() {
		super(WeekControl.class);
	}
}
