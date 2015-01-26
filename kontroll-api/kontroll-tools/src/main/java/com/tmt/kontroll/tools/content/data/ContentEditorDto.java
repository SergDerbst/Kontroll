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
	private String						editScope;
	private String						editScopePattern;

	public List<ContentItem> getContent() {
		return this.content;
	}

	public void setContent(final List<ContentItem> content) {
		this.content = content;
	}

	public String getEditScope() {
		return this.editScope;
	}

	public void setEditScope(final String editScope) {
		this.editScope = editScope;
	}

	public String getEditScopePattern() {
		return this.editScopePattern;
	}

	public void setEditScopePattern(final String editScopePattern) {
		this.editScopePattern = editScopePattern;
	}

	@Override
	public String getDtoClass() {
		return this.getClass().getName();
	}
}
