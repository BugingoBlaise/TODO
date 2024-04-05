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
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Todo entity
 */
@ToString
@Getter
@Setter
@Entity
@Table(name = "my_todo_table")
public class Todo implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private boolean complete=false;
    private String subject;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private Date date;
    private String description;
//    private boolean isdeleted = false;


    //    @OneToMany(mappedBy = "todo",fetch = FetchType.EAGER)
//    private List<User> users;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userss")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Todo() {
    }

    public Todo(String subject) {
        this.subject = subject;
        this.priority = Priority.LOW;
    }

    public Todo(boolean complete, String subject, Priority priority, Date date, String description, Integer id,User user ) {
        this.complete = complete;
        this.subject = subject;
        this.priority = priority;
        this.date = date;
        this.description = description;
        this.id = id;
        this.user=user;

    }

}
