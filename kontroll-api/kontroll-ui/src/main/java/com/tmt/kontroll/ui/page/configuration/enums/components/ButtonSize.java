package com.tmt.kontroll.ui.page.configuration.enums.components;

import com.tmt.kontroll.ui.page.configuration.annotations.components.devices.button.Button;

/**
 * Configuration enum used with {@link Button} annotation. It is used to indicate the
 * size of the button by adding a css class with the same de-capitalized name as the
 * enum value.
 *
 * @author SergDerbst
 *
 */
public enum ButtonSize {

	XLarge,
	Large,
	Medium,
	Small,
	XSmall;
}
