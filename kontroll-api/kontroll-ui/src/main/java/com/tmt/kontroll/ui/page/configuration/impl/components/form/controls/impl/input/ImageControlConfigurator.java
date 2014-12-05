package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.ImageControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link ImageControl}.
 *
 * @author SergDerbst
 *
 */
public class ImageControlConfigurator extends FormControlConfigurator {

	public ImageControlConfigurator() {
		super(ImageControl.class);
	}
}
