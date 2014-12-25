package com.tmt.kontroll.tools.content.data;

import com.tmt.kontroll.context.request.data.json.DataTransferElement;

public class ContentEditorDataLoadingDto implements DataTransferElement {

	private String	currentPath;
	private String	editScope;

	@Override
	public String getDtoClass() {
		return this.getClass().getName();
	}

	public String getEditScope() {
		return this.editScope;
	}

	public void setEditScope(final String editScope) {
		this.editScope = editScope;
	}

	public String getCurrentPath() {
		return this.currentPath;
	}

	public void setCurrentPath(final String currentPath) {
		this.currentPath = currentPath;
	}
}
