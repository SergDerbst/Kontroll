package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.SearchControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link SearchControl}.
 *
 * @author SergDerbst
 *
 */
public class SearchControlConfigurator extends FormControlConfigurator {

	public SearchControlConfigurator() {
		super(SearchControl.class);
	}
}
