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
import org.blaisesolutions.entity.Person;
import org.blaisesolutions.entity.Priority;
import org.blaisesolutions.entity.Todo;
import org.blaisesolutions.entity.User;
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
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.zkoss.zk.ui.util.Clients.alert;

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

    // @Init annotates a initial method
    @Init
    public void init() {
         List<Todo> todoList = todoListService.getTodoList();
         todoListModel = new ListModelList<Todo>(todoList);
        this.filterTodos();
    }
@Command
@NotifyChange({"selectedTodo", "subject", "todoListModel"})
public void addTodo() {
    if (Strings.isBlank(subject)) {
        Clients.showNotification("Subject is blank, nothing to do ?");
    } else {
        String message = todoListService.addTodo(new Todo(subject));
        Clients.showNotification(message);
        subject = null;

         List<Todo> updatedTodoList = todoListService.getTodoList();

         List<Todo> activeTodos = updatedTodoList.stream()
                .filter(t -> !t.isComplete())
                .collect(Collectors.toList());

         todoListModel.clear();
        todoListModel.addAll(activeTodos);
    }
}
    @Command
    @NotifyChange("todoListModel")
    public void updateTodo() {
        if (selectedTodo != null) {
            String mes = todoListService.updateTodo(selectedTodo);
             List<Todo> updatedTodoList = todoListService.getTodoList();
             List<Todo> activeTodos = updatedTodoList.stream()
                    .filter(t -> !t.isComplete())
                    .collect(Collectors.toList());
             todoListModel.clear();
            todoListModel.addAll(activeTodos);
            Clients.showNotification(mes);
        }
    }


   @Command
   @NotifyChange({"selectedTodo", "todoListModel"})
   public void deleteTodo(@BindingParam("todo") Todo todo) {

        org.zkoss.zhtml.Messagebox.show("Do you want to delete Todo?", "Confirmation Delete",
                org.zkoss.zhtml.Messagebox.YES | org.zkoss.zhtml.Messagebox.CANCEL, org.zkoss.zhtml.Messagebox.QUESTION, event -> {
                    if (event.getName().equals(Messagebox.ON_YES)) {
//                deleteUser(newUser);
                        if (todo != null) {
                            String me = todoListService.softDelete(todo);

                            List<Todo> updatedTodoList = todoListService.getTodoList();
                            List<Todo> activeTodos = updatedTodoList.stream()
                                    .filter(t -> !t.isComplete())
                                    .collect(Collectors.toList());
                            todoListModel.clear();
                            todoListModel.addAll(activeTodos);
                            Clients.showNotification(me);
                            alert(me);
                        }
                    }else{
                        alert("Delete cancelled");
                    }
                });




   }

    @Command

    @NotifyChange({"selectedTodo","todoListModel"}) //use postNotifyChange() to notify dynamically
    public void completeTodo(@BindingParam("todo") Todo todo){
       String  res = todoListService.updateTodo(todo);
        if (todo.equals(selectedTodo)) {
            selectedTodo = todo;
              BindUtils.postNotifyChange(null, null, this, "selectedTodo");
        }
    }




     @Command
    @NotifyChange("selectedTodo")
    public void reloadTodo() {
        //do nothing, the selectedTodo will reload by notify change
    }

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
    public void filterTodos() {
        ListModelList<Todo> todos = new ListModelList<>();
         for (Todo todo : this.todoListModel) {
             if (!todo.isComplete()) {
                 todos.add(todo);
            }
        }
         this.todoListModel = todos;
    }
}
