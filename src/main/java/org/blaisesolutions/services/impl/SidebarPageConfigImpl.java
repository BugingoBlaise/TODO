package org.blaisesolutions.services.impl;

import org.blaisesolutions.services.SidebarPage;
import org.blaisesolutions.services.SidebarPageConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SidebarPageConfigImpl implements SidebarPageConfig {
    HashMap<String, SidebarPage> pageMap = new LinkedHashMap<String,SidebarPage>();
    public SidebarPageConfigImpl(){
        pageMap.put("fn1",new SidebarPage("zk","ZK","/imgs/site.png","http://www.zkoss.org/"));
        pageMap.put("fn2",new SidebarPage("demo","ZK Demo","/imgs/demo.png","http://www.zkoss.org/zkdemo"));
        pageMap.put("fn3",new SidebarPage("devref","ZK Developer Reference","/imgs/doc.png","http://books.zkoss.org/wiki/ZK_Developer's_Reference"));
    }

    public List<SidebarPage> getPages(){
        return new ArrayList<SidebarPage>(pageMap.values());
    }

    public SidebarPage getPage(String name){
        return pageMap.get(name);
    }
}
