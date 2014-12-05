package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.UrlControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link UrlControl}.
 *
 * @author SergDerbst
 *
 */
public class UrlControlConfigurator extends FormControlConfigurator {

	public UrlControlConfigurator() {
		super(UrlControl.class);
	}
}
