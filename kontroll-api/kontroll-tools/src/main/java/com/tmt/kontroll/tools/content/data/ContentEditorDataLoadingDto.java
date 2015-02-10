package com.tmt.kontroll.tools.content.data;

import com.tmt.kontroll.context.request.data.json.DataTransferElement;

public class ContentEditorDataLoadingDto implements DataTransferElement {

	private String	currentPath;
	private String	scope;

	@Override
	public String getDtoClass() {
		return this.getClass().getName();
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(final String scope) {
		this.scope = scope;
	}

	public String getCurrentPath() {
		return this.currentPath;
	}

	public void setCurrentPath(final String currentPath) {
		this.currentPath = currentPath;
	}
}
