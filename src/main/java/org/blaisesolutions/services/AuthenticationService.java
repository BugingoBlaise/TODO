/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.blaisesolutions.services;

import org.blaisesolutions.entity.UserCredential;

public interface AuthenticationService {
	public static final String NAME = "AuthenticationServiceImpl";
	/**login with account and password**/
	public boolean login(String account, String password);
	
	/**logout current user**/
	public void logout();
	
	/**get current user credential**/
	public UserCredential getUserCredential();
	
}
