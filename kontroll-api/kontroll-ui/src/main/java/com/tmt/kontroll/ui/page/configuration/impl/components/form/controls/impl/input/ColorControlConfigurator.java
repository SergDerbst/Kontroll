package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.ColorControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link ColorControl}.
 *
 * @author SergDerbst
 *
 */
public class ColorControlConfigurator extends FormControlConfigurator {

	public ColorControlConfigurator() {
		super(ColorControl.class);
	}
}
