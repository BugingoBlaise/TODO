/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.blaisesolutions.services;


import org.blaisesolutions.entity.Todo;
import org.blaisesolutions.entity.User;
import org.jboss.logging.Param;

import java.util.List;
import java.util.Optional;

public interface UserInfoService {

	public static final String NAME = "UserinfoServiceImpl";
	/** find user by account **/
	public User findUser(String account);
	
	/** update user **/
	public String updateUser(User user);

	public String create(User user);
	public List<User> findAll();

	List<User> searchUserByName(String keyword);
 	/*
	public void update(User user);

	public User findById(int id);
	public User findByFullName(String subject);
	public User findByEmail(String email);

 */
}
