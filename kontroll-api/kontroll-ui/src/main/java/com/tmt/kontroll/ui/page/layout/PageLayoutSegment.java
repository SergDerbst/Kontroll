package com.tmt.kontroll.ui.page.layout;

import java.util.List;


public abstract class PageLayoutSegment implements PageLayoutScope {
	
	public abstract List<PageLayoutSegment> getChildren(); 
}
