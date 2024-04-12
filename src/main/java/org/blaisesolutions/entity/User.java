/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.blaisesolutions.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * User entity
 */

//        @NamedQuery(name = "Account.findByAcc" ,query = "select a from Account a where a.versionId =: accountId"),
//        @NamedQuery(name = "Account.findAccByLike", query = "select ac from Account ac where ac.versionId like :key")

@NamedQuery(name = "User.searchUserByName", query = "SELECT u FROM User u WHERE u.fullName LIKE :kw")
@NamedQuery(name = "User.listActiveUsers", query = "SELECT u FROM User u WHERE u.isdeleted = false")
@Getter
@Setter
@Entity
@Table(name = "my_users_table")
public class User implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String account;
    private String fullName;
    private String password;
    private String email;
    private Date birthday;
    private String country;
    private String bio;
    private String role;
    private boolean isdeleted = false;


//    @ManyToOne
//    @JoinColumn(name = "todos_id")
//    private Todo todo;

    public User() {
    }

    public User(int id, String account, String fullName, String password, String email, Date birthday, String country, String bio, String role, boolean isdeleted) {
        this.id = id;
        this.account = account;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.country = country;
        this.bio = bio;
        this.role = role;
        this.isdeleted = isdeleted;
//        this.todo=todo;
    }
}
