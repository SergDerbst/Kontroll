package com.tmt.kontroll.ui.components.utils.content;

import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.Form;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.configuration.annotations.general.Hidden;

@Hidden
@PageConfig(contexts = {@PageContext(scope = "page.contentEditorForm")})
@Form(dtoClass = ContentEditorDto.class, name = "contentEditorForm", targetUrl = "/submit/contentEditor")
public class ContentEditorForm extends PageSegment {

	public ContentEditorForm() {
		super();
	}
}
