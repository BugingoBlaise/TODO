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
import org.blaisesolutions.services.impl.SidebarPageConfigImpl;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

import java.util.List;

public class SidebarViewModel {

    private SidebarPageConfig pageConfig = new SidebarPageConfigImpl();

    public List<SidebarPage> getSidebarPages() {
        return pageConfig.getPages();
    }

    @Command
    public void navigate(@BindingParam("page") SidebarPage page) {
        Executions.getCurrent().sendRedirect(page.getUri());
    }
}
