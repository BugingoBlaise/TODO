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
import org.blaisesolutions.entity.Priority;
import org.blaisesolutions.entity.Todo;
import org.blaisesolutions.services.TodoListService;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TodoListViewModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @WireVariable
    TodoListService todoListService;
    Todo updatetodo;
    String subject;
    ListModelList<Todo> todoListModel;
    Todo selectedTodo;
    Todo newTodo;
    boolean complete;
    Priority priority;
    Date date;
    String description;



    public List<Priority> getPriorityList() {
        return Arrays.asList(Priority.values());
    }


    @Init // @Init annotates a initial method
    public void init() {
        //get data from service and wrap it to model for the view
        List<Todo> todoList = todoListService.getTodoList();
        //you can use List directly, however use ListModelList provide efficient control in MVVM
        todoListModel = new ListModelList<Todo>(todoList);
    }

    @Command //@Command annotates a command method
    @NotifyChange({"selectedTodo", "subject"})
    //@NotifyChange annotates data changed notification after calling this method
    public void addTodo() {
        if (Strings.isBlank(subject)) {
            Clients.showNotification("Subject is blank, nothing to do ?");
        } else {
            String message = todoListService.addTodo(new Todo(subject));
            Clients.showNotification(message);
            subject = null;
            // Fetch the updated list of todos from the service
            List<Todo> updatedTodoList = todoListService.getTodoList();
            // Update the todoListModel with the new list of todos
            todoListModel.clear();
            todoListModel.addAll(updatedTodoList);
        }
    }

    @Command
    public void updateTodo() {
            if (selectedTodo != null) {
//                    todo.setSubject(selectedTodo.getSubject());
//                    todo.setPriority(selectedTodo.getPriority());
//                    todo.setDate(selectedTodo.getDate());
//                    todo.setDescription(selectedTodo.getDescription());
                  String mes=  todoListService.updateTodo(selectedTodo);
                    List<Todo> updatedTodoList = todoListService.getTodoList();
                    // Update the todoListModel with the new list of todos
                    todoListModel.clear();
                    todoListModel.addAll(updatedTodoList);
           Clients.showNotification(mes);
            }
    }

    @Command
    public void deleteTodo(@BindingParam("todo") Todo todo) {
        //save data
       /* todoListService.deleteTodo(todo);
        todoListModel.remove(todo);
        if (todo.equals(selectedTodo)) {
            //refresh selected todo view
            selectedTodo = null;
            //for the case that notification is decided dynamically
            BindUtils.postNotifyChange(null, null, this, "selectedTodo");
        }

        */

        // Mark the todo as deleted by setting its complete status to true
        todo.setComplete(true);

        // Save the updated todo to reflect the change in the database
        todoListService.updateTodo(todo);

        // Refresh the todo list model to reflect the changes in the interface
        List<Todo> updatedTodoList = todoListService.getTodoList();
        todoListModel.clear();
        todoListModel.addAll(updatedTodoList);

        // If the deleted todo is currently selected, clear the selection
        if (todo.equals(selectedTodo)) {
            selectedTodo = null;
            BindUtils.postNotifyChange(null, null, this, "selectedTodo");
        }
    }


    @Command
    @NotifyChange("selectedTodo") //use postNotifyChange() to notify dynamically
    public void completeTodo(@BindingParam("todo") Todo todo) {
        //save data
      String  res = todoListService.updateTodo(todo);
        if (todo.equals(selectedTodo)) {
            selectedTodo = todo;
            //for the case that notification is decided dynamically
            //you can use BindUtils.postNotifyChange to notify a value changed
            BindUtils.postNotifyChange(null, null, this, "selectedTodo");
        }
    }




    //when user clicks the update button
    @Command
    @NotifyChange("selectedTodo")
    public void reloadTodo() {
        //do nothing, the selectedTodo will reload by notify change
    }

    //the validator is the class to validate data before set ui data back to todo
    public Validator getTodoValidator() {
        return new AbstractValidator() {

            public void validate(ValidationContext ctx) {
                //get the form that will be applied to todo
                Todo todo = (Todo) ctx.getProperty().getValue();

                if (Strings.isBlank(todo.getSubject())) {
                    Clients.showNotification("Subject is blank, nothing to do ?");
                    //mark the validation is invalid, so the data will not update to bean
                    //and the further command will be skipped.
                    ctx.setInvalid();
                }
            }
        };
    }

}
