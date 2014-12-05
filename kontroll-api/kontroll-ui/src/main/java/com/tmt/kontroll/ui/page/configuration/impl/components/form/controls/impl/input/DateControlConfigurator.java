package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.DateControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link DateControl}.
 *
 * @author SergDerbst
 *
 */
public class DateControlConfigurator extends FormControlConfigurator {

	public DateControlConfigurator() {
		super(DateControl.class);
	}
}
