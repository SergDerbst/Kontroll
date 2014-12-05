package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.label;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label.LabelControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link LabelControl}.
 *
 * @author SergDerbst
 *
 */
public class LabelControlConfigurator extends FormControlConfigurator {

	public LabelControlConfigurator() {
		super(LabelControl.class);
	}
}
