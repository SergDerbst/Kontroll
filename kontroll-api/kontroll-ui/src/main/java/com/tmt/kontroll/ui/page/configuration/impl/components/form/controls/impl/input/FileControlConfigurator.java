package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.FormControl;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.FileControl;
import com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.FormControlConfigurator;

/**
 * Configures page elements configured with {@link FormControl} annotation {@link FileControl}.
 *
 * @author SergDerbst
 *
 */
public class FileControlConfigurator extends FormControlConfigurator {

	public FileControlConfigurator() {
		super(FileControl.class);
	}
}
