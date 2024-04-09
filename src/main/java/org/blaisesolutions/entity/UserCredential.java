package org.blaisesolutions.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserCredential implements Serializable {

    private static final long serialVersionUID = 1L;

    String account;
    String name;
    String ROLE;

    Set<String> roles = new HashSet<String>();

    public UserCredential(String account, String name,String ROLE) {
        this.account = account;
        this.name = name;
        this.ROLE=ROLE;
    }

    public UserCredential() {
        this.account = "anonymous";
        this.name = "Anonymous";
        roles.add("anonymous");

    }

    public boolean isAnonymous() {
        return hasRole("anonymous") || "anonymous".equals(account);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasRole(String role){
        return roles.contains(role);
    }

    public void addRole(String role){
        roles.add(role);
    }

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }
}
