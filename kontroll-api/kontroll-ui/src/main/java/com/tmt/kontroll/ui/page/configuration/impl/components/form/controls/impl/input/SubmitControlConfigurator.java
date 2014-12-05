package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.SubmitControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link SubmitControl}.
 *
 * @author SergDerbst
 *
 */
public class SubmitControlConfigurator extends FormControlConfigurator {

	public SubmitControlConfigurator() {
		super(SubmitControl.class);
	}
}
