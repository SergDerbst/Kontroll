package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.MonthControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link MonthControl}.
 *
 * @author SergDerbst
 *
 */
public class MonthControlConfigurator extends FormControlConfigurator {

	public MonthControlConfigurator() {
		super(MonthControl.class);
	}
}
