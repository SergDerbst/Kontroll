package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.select;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.SelectControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link SelectControl}.
 *
 * @author SergDerbst
 *
 */
public class SelectControlConfigurator extends FormControlConfigurator {

	public SelectControlConfigurator() {
		super(SelectControl.class);
	}
}
