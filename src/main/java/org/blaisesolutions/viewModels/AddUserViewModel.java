package org.blaisesolutions.viewModels;

import org.blaisesolutions.entity.User;
import org.blaisesolutions.entity.UserCredential;
import org.blaisesolutions.services.CommonInfoService;
import org.blaisesolutions.services.UserInfoService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.blaisesolutions.services.CommonInfoService.getCountryList;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AddUserViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @WireVariable
    private UserInfoService userInfoService;

    private User user;
    private String confirmPassword;
    private final List<String> countryList = CommonInfoService.getCountryList();

    // Getter and setter methods for user, confirmPassword, and countryList

    @Init
    public void init(){
        this.user=new User();
    }
    @Command
    @NotifyChange({"user", "confirmPassword"})
    public void signup() {
        // Validate passwords match
        if (!user.getPassword().equals(confirmPassword)) {
            // Show error message
            Clients.showNotification("Passwords do not match");
            return;
        }
        user.setFullName(user.getFullName());
        user.setEmail(user.getEmail());
        user.setAccount("USER");
        user.setBirthday(user.getBirthday());
        user.setCountry(user.getCountry());
        user.setBio(user.getBio());
        user.setPassword(user.getPassword());
        user.setRole("USER");


        // Perform signup logic
        String result = userInfoService.create(user);
        // Show success or error message
        Clients.showNotification(result);
    }

    @Command
    public void login(){
        Executions.getCurrent().sendRedirect("login.zul");
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public List<String> getCountryList() {
        return countryList;
    }


    // Other methods if needed
}


