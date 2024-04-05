/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.blaisesolutions.services.impl;



import lombok.extern.slf4j.Slf4j;
import org.blaisesolutions.dao.TodoDao;
import org.blaisesolutions.entity.Person;
import org.blaisesolutions.entity.Todo;
import org.blaisesolutions.entity.User;
import org.blaisesolutions.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service("todoListService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TodoListServiceImpl implements TodoListService {

	TodoDao todoDao;

	@Autowired
	public TodoListServiceImpl(TodoDao todoDao) {
		this.todoDao = todoDao;
	}


    @Override
    public List<Todo> getTodoList() {
        return todoDao.queryAll();
    }

    @Override
    public List getTodo(Integer id) {
        return (List) todoDao.get(id);
    }
/*
    public void saveTodo(Todo todo) {
		Optional<Todo> existingTodo = todoDao.getTodoById(todo.getId());
		if (existingTodo.isPresent()) {
			throw new IllegalArgumentException("Todo with id " + todo.getId() + " already exists");
		}
		todoDao.saveOrUpdate(todo);
	}

 */

    @Override
    public String updateTodo(Todo todo) {
        Optional<Todo>existingTodo= todoDao.getTodoById(todo);

        if(!existingTodo.isPresent()){
            throw new IllegalArgumentException("Todo not found");
        }else{
            todoDao.updateTodo(todo);
            return "Updated successfully";
        }

    }

    @Override
    public void deleteTodo(Todo todo) {
        todoDao.delete(todo);
    }

    @Override
    public String addTodo(Todo todo) {
        return todoDao.save(todo);
    }
    @Override
    public List<Todo> findByUser(User user) {
        return this.todoDao.findByUser(user);
    }
    @Override
    public String softDelete(Todo todo) {
        try{
            todoDao.softDelete(todo);
            return "Deleted successfully";
        }catch (Exception ex){
            log.error("Error deleting todo: {}", ex.getMessage());
            return "Failed to delete todo: " + ex.getMessage();
        }
    }
}




