/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.blaisesolutions.services.impl;


import org.blaisesolutions.services.SidebarPage;
import org.blaisesolutions.services.SidebarPageConfig;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
@Component("sidebarPageConfigAjaxbased")
@Scope(value="request",proxyMode= ScopedProxyMode.TARGET_CLASS)
public class SidebarPageConfigAjaxBasedImpl implements SidebarPageConfig {
	HashMap<String, SidebarPage> pageMap = new LinkedHashMap<String,SidebarPage>();
	public SidebarPageConfigAjaxBasedImpl(){		
		pageMap.put("zk",new SidebarPage("zk","www.zkoss.org","/imgs/site.png","http://www.zkoss.org/"));
		pageMap.put("demo",new SidebarPage("demo","ZK Demo","/imgs/demo.png","http://www.zkoss.org/zkdemo"));
		pageMap.put("devref",new SidebarPage("devref","ZK Developer Reference","/imgs/doc.png","http://books.zkoss.org/wiki/ZK_Developer's_Reference"));
		
 		pageMap.put("fn2",new SidebarPage("fn2","Profile ","/imgs/fn.png","/profile.zul"));
 		pageMap.put("fn3",new SidebarPage("fn3","Users", "/imgs/users.png","/userslist.zul"));
		 pageMap.put("fn4",new SidebarPage("fn4","Todo List ","/imgs/fn.png","/todolist.zul"));
 	}
	
	public List<SidebarPage> getPages(){
		return new ArrayList<SidebarPage>(pageMap.values());
	}
	
	public SidebarPage getPage(String name){
		return pageMap.get(name);
	}
	
}