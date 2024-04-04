package org.blaisesolutions.viewModels;

import lombok.Getter;
import lombok.Setter;
import org.blaisesolutions.entity.Todo;
import org.blaisesolutions.entity.User;
import org.blaisesolutions.services.TodoListService;
import org.blaisesolutions.services.UserInfoService;
import org.zkoss.bind.annotation.ExecutionParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.annotation.Command;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UserDetailsViewModel {
    @WireVariable
    private UserInfoService userInfoService;
    private User user;
    @WireVariable
    private TodoListService todoListService;
    private List<Todo> userTodos = new ArrayList<>();
    private Todo selectedTodo;
    private List<Todo> todoList = new ArrayList<>();

    @Init
    public void init(@ExecutionParam("user") final User user) {
        System.out.println(user.getFullName());
        this.user = user;
        // Load all tasks associated with the user
        this.userTodos = todoListService.findByUser(user);
        // Load all tasks available in the system
        this.todoList = this.todoListService.getTodoList();
        this.filterTodos();
    }
    @Command
    @NotifyChange({"userTodos","todoList","selectedTodo"})
    public void addTodo() {
        if (selectedTodo != null) {
            this.selectedTodo.setUser(this.user);
            String res = this.todoListService.updateTodo(this.selectedTodo);
            this.userTodos = this.todoListService.findByUser(user);
            this.selectedTodo = null;
            this.filterTodos();
            Clients.showNotification(res);
        } else {
            Clients.showNotification("Can't assign todo");
        }
    }

    public void filterTodos() {
        List<Todo> todos = new ArrayList<>();
        for (Todo todo : this.todoList) {
            if (!this.userTodos.contains(todo)) {
                todos.add(todo);
            }
        }
        this.todoList = todos;
    }

}
