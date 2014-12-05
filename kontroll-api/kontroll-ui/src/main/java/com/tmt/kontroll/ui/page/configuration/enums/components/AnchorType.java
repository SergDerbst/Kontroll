package com.tmt.kontroll.ui.page.configuration.enums.components;

import com.tmt.kontroll.ui.page.configuration.annotations.components.devices.anchor.Anchor;

/**
 * Configuration annotation for {@link Anchor}. It indicates what type of anchor is to
 * be rendered according to the value {@link Anchor#display}.
 *
 * @author SergDerbst
 *
 */
public enum AnchorType {
	Plain,
	Content,
	Caption;
}
