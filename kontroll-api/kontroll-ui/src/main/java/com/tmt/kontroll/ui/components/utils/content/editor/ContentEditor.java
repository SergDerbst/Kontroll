package com.tmt.kontroll.ui.components.utils.content.editor;

import com.tmt.kontroll.ui.page.configuration.annotations.components.devices.anchor.Anchor;
import com.tmt.kontroll.ui.page.configuration.annotations.components.text.Header;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.configuration.annotations.general.Modal;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.segments.PageSegment;

@PageConfig(contexts = {@PageContext(scope = "page.contentEditor")})
@Modal
@Anchor(ordinal = 0, position = ChildPosition.Top, name = "close", href = "#close", title = "Close")
@Header(ordinal = 1, position = ChildPosition.Top, name = "dialogHeader", level = 2)
public class ContentEditor extends PageSegment {

}
