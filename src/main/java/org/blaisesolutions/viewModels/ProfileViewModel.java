/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.blaisesolutions.viewModels;

import lombok.Getter;
import lombok.Setter;
import org.blaisesolutions.entity.User;
import org.blaisesolutions.services.CommonInfoService;
import org.blaisesolutions.services.UserInfoService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ProfileViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @WireVariable
    private UserInfoService userInfoService;

    private User user;

    private final List<String> countryList = CommonInfoService.getCountryList();

    // Getter and setter methods for user, confirmPassword, and countryList

    @Init
    public void init() {
        this.user = new User();
    }

    @Command
    @NotifyChange("user")
    public void save() {
        user.setFullName(user.getFullName());
        user.setEmail(user.getEmail());
        user.setAccount("USER");
        user.setBirthday(user.getBirthday());
        user.setCountry(user.getCountry());
        user.setBio(user.getBio());
        user.setRole("USER");
        // Perform signup logic
        String result = userInfoService.create(user);
        // Show success or error message
        Clients.showNotification(result);
    }
    @NotifyChange("user")
    @Command
    public void reload() {
        user = null;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<String> getCountryList() {
        return countryList;
    }
}
