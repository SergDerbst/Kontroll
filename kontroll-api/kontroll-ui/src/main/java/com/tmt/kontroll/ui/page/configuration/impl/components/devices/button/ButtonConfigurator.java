package com.tmt.kontroll.ui.page.configuration.impl.components.devices.button;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.page.configuration.annotations.components.devices.button.Button;
import com.tmt.kontroll.ui.page.configuration.annotations.event.Event;
import com.tmt.kontroll.ui.page.configuration.enums.components.ButtonType;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.CaptionHandler;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.CssHandler;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.EventHandler;
import com.tmt.kontroll.ui.page.configuration.impl.components.layout.ChildElementConfigurator;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configurator for the {@link Button} annotation. It adds a button child element
 * to the given {@link PageSegment} according to the annotation's configuration.
 *
 * @author SergDerbst
 *
 */
@Component
public class ButtonConfigurator extends ChildElementConfigurator {

	@Autowired
	CaptionHandler	captionHandler;

	@Autowired
	CssHandler			cssHandler;

	@Autowired
	EventHandler		eventHandler;

	public ButtonConfigurator() {
		super(Button.class);
	}

	@Override
	public void configure(final PageSegment segment) {
		for (final Button config : segment.getClass().getAnnotationsByType(Button.class)) {
			if (super.isNotAddedYet(segment, config, "name")) {
				final PageSegment button = new PageSegment(HtmlTag.Button) {};
				button.setParentScope(segment.getDomId());
				button.setScope(config.name());
				this.handleCss(button, config);
				this.handleEvents(config, button);
				this.handleButtonStandardType(config, button);
				this.captionHandler.handle(config, button, config.caption(), config.name());
				super.addChild(config.position(), segment, button);
			}
		}
	}

	private void handleEvents(final Button config, final PageSegment button) {
		for (final Event event : config.events()) {
			this.eventHandler.handle(event, button);
		}
	}

	private void handleCss(final PageSegment button, final Button config) {
		this.cssHandler.handle(button, config.cssClass());
		this.cssHandler.handle(button, "btn-" + StringUtils.uncapitalize(config.size().name()));
		if (ButtonType.Dropdown == config.type()) {
			this.cssHandler.handle(button, "btn-dropdown");
		}
	}

	private void handleButtonStandardType(final Button config, final PageSegment button) {
		if (this.isStandardButton(config)) {
			button.getAttributes().put("type", StringUtils.uncapitalize(config.type().name()));
		}
	}

	private boolean isStandardButton(final Button config) {
		return ButtonType.Button == config.type() || ButtonType.Reset == config.type() || ButtonType.Submit == config.type();
	}
}
