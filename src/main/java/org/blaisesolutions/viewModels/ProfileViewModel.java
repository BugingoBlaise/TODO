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
import org.zkoss.bind.annotation.ExecutionParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zuti.zul.Apply;

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
        user.setPassword("12345");
        // Perform signup logic
        String result = userInfoService.create(user);
        // Show success or error message
        Clients.showNotification(result);
    }
    @NotifyChange("user")
    @Command
    public void reload() {
//        user = null;
    }
    @org.zkoss.zk.ui.annotation.Command("openUsersview")
    public void openUsersview() {
        applyUserview("/userslist.zul");
    }

    public void applyUserview(String uri) {
        Apply apply = (Apply) Selectors.find("::shadow#content").iterator().next();
        apply.setTemplate(null);
        apply.setTemplateURI(uri);
        apply.recreate();
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
