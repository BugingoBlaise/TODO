/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.blaisesolutions.viewModels;


import org.blaisesolutions.services.SidebarPage;
import org.blaisesolutions.services.SidebarPageConfig;
import org.blaisesolutions.services.impl.SidebarPageConfigAjaxBasedImpl;

import java.util.List;

public class SidebarAjaxbasedViewModel {

	//todo: wire service
	private SidebarPageConfig pageConfig = new SidebarPageConfigAjaxBasedImpl();
	
	public List<SidebarPage> getSidebarPages() {
		return pageConfig.getPages();
	}
}
