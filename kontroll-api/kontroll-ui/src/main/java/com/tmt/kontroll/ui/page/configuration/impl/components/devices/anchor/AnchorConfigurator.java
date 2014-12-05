package com.tmt.kontroll.ui.page.configuration.impl.components.devices.anchor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.page.configuration.annotations.components.devices.anchor.Anchor;
import com.tmt.kontroll.ui.page.configuration.enums.components.ValueSourceType;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.CaptionHandler;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.CssHandler;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.ValueSourceHandler;
import com.tmt.kontroll.ui.page.configuration.impl.components.layout.ChildElementConfigurator;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configurator for {@link PageSegment}s annotated with {@link Anchor}. It adds an anchor child
 * element to the annotated page segment according to the annotation's values.
 *
 * @author SergDerbst
 *
 */
@Component
public class AnchorConfigurator extends ChildElementConfigurator {

	@Autowired
	CaptionHandler			captionHandler;

	@Autowired
	CssHandler					cssHandler;

	@Autowired
	ValueSourceHandler	valueSourceHandler;

	public AnchorConfigurator() {
		super(Anchor.class);
	}

	@Override
	public void configure(final PageSegment segment) {
		for (final Anchor config : segment.getClass().getAnnotationsByType(Anchor.class)) {
			if (super.isNotAddedYet(segment, config, "name")) {
				final PageSegment anchor = new PageSegment(HtmlTag.Anchor) {};
				anchor.setParentScope(segment.getDomId());
				anchor.setScope(config.name());
				anchor.getAttributes().put("href", config.href());
				anchor.getAttributes().put("title", config.title());
				this.cssHandler.handle(segment, config.cssClass());
				if (this.valueSourceIsCaption(config)) {
					this.captionHandler.handle(config, anchor, config.valueSource().source(), config.name());
				} else {
					this.valueSourceHandler.handle(config.valueSource(), anchor);
				}
				super.addChild(config.position(), segment, anchor);
			}
		}
	}

	private boolean valueSourceIsCaption(final Anchor config) {
		return ValueSourceType.Caption == config.valueSource().type();
	}
}
