package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.DatetimeLocalControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link DatetimeLocalControl}.
 *
 * @author SergDerbst
 *
 */
public class DatetimeLocalControlConfigurator extends FormControlConfigurator {

	public DatetimeLocalControlConfigurator() {
		super(DatetimeLocalControl.class);
	}
}
