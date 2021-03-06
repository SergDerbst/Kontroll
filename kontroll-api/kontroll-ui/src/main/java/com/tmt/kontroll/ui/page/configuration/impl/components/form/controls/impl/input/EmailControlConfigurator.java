package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.EmailControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link EmailControl}.
 *
 * @author SergDerbst
 *
 */
public class EmailControlConfigurator extends FormControlConfigurator {

	public EmailControlConfigurator() {
		super(EmailControl.class);
	}
}
