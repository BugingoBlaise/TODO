/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.blaisesolutions.services.impl;


import org.blaisesolutions.dao.UserDao;
import org.blaisesolutions.entity.Todo;
import org.blaisesolutions.entity.User;
import org.blaisesolutions.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zul.ListModelList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

//@Transactional
@Service("userInfoService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserInfoServiceImpl implements UserInfoService, Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    UserDao userDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User findUser(String account) {
        Optional<User> userOptional = userDao.findUserByEmail(account);

        return userOptional.orElse(null);
    }

    @Override
    public String updateUser(User user) {
        Optional<User> existingUser = userDao.getUserById(user);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User not found");
        } else {
            // Update the user entity with the new list of todos
//            existingUser.get().setTodo(user.getTodo());
            userDao.update(existingUser.get());
            return "Assigned Successfully";
        }
    }


    @Override
    public String create(User user) {

        // Check if user already exists
        User existingUser = findUser(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("User already exists");
        }
        if(user.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userDao.save(user);
        }else{
            return userDao.save(user);
        }
        // Encode password before saving

        // Save the user

    }

    @Override
    public List<User> findAll() {
        return userDao.queryAll();
    }

    @Override
    public List<User> searchUserByName(String name) {
        List<User> allUsers = findAll();
        List<User> result = new LinkedList<>();

        if (name == null || name.isEmpty()) {
            result.addAll(allUsers); // Add all users if the search name is empty
        } else {
            // Iterate through all users and check if the name matches
            for (User user : allUsers) {
                if (
                        user.getFullName().toLowerCase().contains(name.toLowerCase())
                                || user.getEmail().toLowerCase().contains(name.toLowerCase())) {
                    result.add(user);
                }
            }
        }

        return result;

    }

}
