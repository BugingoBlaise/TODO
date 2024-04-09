/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.blaisesolutions.controller;



import org.blaisesolutions.entity.UserCredential;
import org.blaisesolutions.services.AuthenticationService;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Initiator;

import java.util.Map;
//@VariableResolver(va)
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AuthenticationInit implements Initiator {
	//services
	@WireVariable
	AuthenticationService authService;

	public void doInit(Page page, Map<String, Object> args) throws Exception {
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		UserCredential cre = authService.getUserCredential();
		if(cre==null || cre.isAnonymous()){
			Executions.sendRedirect("/login.zul");
			return;
		}
	}
}