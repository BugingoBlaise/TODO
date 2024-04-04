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

import java.util.List;

public interface TodoListService {

	/** get Todo list **/
	List<Todo> getTodoList();


	/**
	 * get Todo by id
	 **/
	List getTodo(Integer id);

	/** save Todo **/
//	void saveTodo(Todo todo);

	/** update Todo **/
	String updateTodo(Todo todo);

	/** delete Todo **/
	void deleteTodo(Todo todo);

	String addTodo(Todo todo);
	List<Todo> findByUser(User user);

}
