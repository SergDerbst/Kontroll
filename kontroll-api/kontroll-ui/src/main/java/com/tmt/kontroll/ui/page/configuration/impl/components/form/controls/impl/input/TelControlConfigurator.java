package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.TelControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link TelControl}.
 *
 * @author SergDerbst
 *
 */
public class TelControlConfigurator extends FormControlConfigurator {

	public TelControlConfigurator() {
		super(TelControl.class);
	}
}
