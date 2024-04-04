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
/*
    public void deleteTodoById(Integer id) {
		try {
			todoDao.deleteTodoById(id);
		} catch (DataAccessException ex) {
			// Log the exception and perform appropriate error handling such as
			// returning a failure message or rethrowing the exception.
			throw ex;
		}
	}

 */
//	public void updateTodo(Todo)




	/*
        static int todoId = 0;
        static List<Todo> todoList = new ArrayList<Todo>();
        static{
            todoList.add(new Todo(todoId++,"Buy some milk", Priority.LOW,null,null));
            todoList.add(new Todo(todoId++,"Dennis' birthday gift",Priority.MEDIUM,dayAfter(10),null));
            todoList.add(new Todo(todoId++,"Pay credit-card bill",Priority.HIGH,dayAfter(5),"$1,000"));
        }


        private static Date dayAfter(int d){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, d);
            return c.getTime();
        }

        /** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	/*public  List<Todo>getTodoList() {
		List<Todo> list = new ArrayList<Todo>();
		for(Todo todo:todoList){
			list.add(Todo.clone(todo));
		}
		return todoDao.queryAll();
	}

	 */

}




	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	/*public synchronized Todo getTodo(Integer id){
		int size = todoList.size();
		for(int i=0;i<size;i++){
			Todo t = todoList.get(i);
			if(t.getId().equals(id)){
				return Todo.clone(t);
			}
		}
		return null;
	}

	 */

	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	/*public synchronized Todo saveTodo(Todo todo){
		todo = Todo.clone(todo);
		todo.setId(todoId++);
		todoList.add(todo);
		return todo;
	}

	 */

	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	 /*public synchronized Todo updateTodo(Todo todo){
		if(todo.getId()==null){
			throw new IllegalArgumentException("cann't save a null-id todo, save it first");
		}else{
			todo = Todo.clone(todo);
			int size = todoList.size();
			for(int i=0;i<size;i++){
				Todo t = todoList.get(i);
				if(t.getId().equals(todo.getId())){
					todoList.set(i, todo);
					return todo;
				}
			}
			throw new RuntimeException("Todo not found "+todo.getId());
		}
	}



	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	/*public synchronized void deleteTodo(Todo todo){
		if(todo.getId()!=null){
			int size = todoList.size();
			for(int i=0;i<size;i++){
				Todo t = todoList.get(i);
				if(t.getId().equals(todo.getId())){
					todoList.remove(i);
					return;
				}
			}
		}
	}
	*/

