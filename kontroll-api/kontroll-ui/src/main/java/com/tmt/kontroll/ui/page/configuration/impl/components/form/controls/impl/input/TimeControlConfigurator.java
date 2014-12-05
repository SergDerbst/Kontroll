package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.TimeControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link TimeControl}.
 *
 * @author SergDerbst
 *
 */
public class TimeControlConfigurator extends FormControlConfigurator {

	public TimeControlConfigurator() {
		super(TimeControl.class);
	}
}
