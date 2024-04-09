/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.blaisesolutions.services;


import org.blaisesolutions.entity.User;

import java.util.List;

public interface UserInfoService {

	public static final String NAME = "UserinfoServiceImpl";
	/** find user by account **/
	public User findUser(String account);
	
	/** update user **/
	public String updateUser(User user);

	public String create(User user);
	public List<User> findAll();

	List<User> searchUserByName(String keyword);

    void deleteUser(User selectedUser);
	 String softDelete(User user);
}
