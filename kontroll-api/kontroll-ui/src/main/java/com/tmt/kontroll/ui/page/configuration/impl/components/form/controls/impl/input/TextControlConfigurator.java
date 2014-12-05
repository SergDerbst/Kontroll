package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.TextControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link TextControl}.
 *
 * @author SergDerbst
 *
 */
public class TextControlConfigurator extends FormControlConfigurator {

	public TextControlConfigurator() {
		super(TextControl.class);
	}
}
