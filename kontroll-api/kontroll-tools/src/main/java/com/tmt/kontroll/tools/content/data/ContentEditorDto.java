package com.tmt.kontroll.tools.content.data;

import java.util.List;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.context.request.data.json.DataTransferElement;
import com.tmt.kontroll.tools.content.ui.elements.editor.ContentEditorForm;

/**
 * Request param DTO for submission of the {@link ContentEditorForm}.
 *
 * @author SergDerbst
 *
 */
public class ContentEditorDto implements DataTransferElement {

	private List<ContentItem>	content;
	private String						scope;
	private String						requestPattern;

	public List<ContentItem> getContent() {
		return this.content;
	}

	public void setContent(final List<ContentItem> content) {
		this.content = content;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(final String scope) {
		this.scope = scope;
	}

	public String getRequestPattern() {
		return this.requestPattern;
	}

	public void setRequestPattern(final String requestPattern) {
		this.requestPattern = requestPattern;
	}

	@Override
	public String getDtoClass() {
		return this.getClass().getName();
	}
}
