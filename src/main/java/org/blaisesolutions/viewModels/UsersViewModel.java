package org.blaisesolutions.viewModels;


import org.blaisesolutions.entity.Todo;
import org.blaisesolutions.entity.User;
import org.blaisesolutions.services.TodoListService;
import org.blaisesolutions.services.UserInfoService;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.annotation.Command;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;
import org.zkoss.zuti.zul.Apply;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.zkoss.zk.ui.util.Clients.alert;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UsersViewModel {
    @WireVariable
    private UserInfoService userInfoService;
    private User user;
    private List<User> users = new ListModelList<>();

    private User selectedUser;
    private User newUser;


    @WireVariable
    private TodoListService todoListService;

    private List<Todo> todos = new LinkedList<>();

    private String keyword;

    private List<Todo> allTodos;

    private Todo selectedTodo;

    @Init
    public void init() {
        this.users = userInfoService.findAll();
        this.allTodos = this.todoListService.getTodoList();
        this.filterUsers();
    }

    @Command("search")
    @NotifyChange("users")
    public void search() {
        users.clear();
        users.addAll(userInfoService.searchUserByName(keyword));
    }

    @Command("openProfilePage")
    public void openProfilePage() {
        applyProfileView("/profile.zul");
    }

    public void applyProfileView(String uri) {
        Apply apply = (Apply) Selectors.find("::shadow#content").iterator().next();
        apply.setTemplate(null);
        apply.setTemplateURI(uri);
        apply.recreate();
    }

    public void applyUserPage(String uri) {
        Apply apply = (Apply) Selectors.find("::shadow#content")
                .iterator().next();
        Execution execution = Executions.getCurrent();
        execution.setAttribute("user", this.selectedUser);
        apply.setTemplate(null);
        apply.setTemplateURI(uri);
        apply.recreate();
    }

    @NotifyChange({"todos", "selectedUser"})
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
//        this.setTodos(this.todoListService.findByUser(selectedUser));
        Session sess = Sessions.getCurrent();
        sess.setAttribute("selectedUser", this.selectedTodo);
        this.applyUserPage("/user-details.zul");
    }


    @Command("confirmDelete")
    @NotifyChange("users")
    public void confirmDelete(@BindingParam("user") User userToDelete){
        Messagebox.show("Do you want to delete user?", "Confirmation Delete",
                Messagebox.YES | Messagebox.CANCEL, Messagebox.QUESTION, event -> {
            if (event.getName().equals(Messagebox.ON_YES)) {
//                deleteUser(newUser);
                if (userToDelete != null) {
                    System.out.println("---method reached----");
                    userInfoService.softDelete(userToDelete);
                    List<User>updatedUsers=userInfoService.findAll();
                    List<User>activeUsers=updatedUsers
                            .stream()
                            .filter(t->!t.isIsdeleted())
                            .collect(Collectors.toList());
                    users.clear();
                    users.addAll(activeUsers);
                    alert("Deleted successfully");
                }

            }else{
                alert("Delete cancelled");
            }
        });

    }
    public void filterUsers() {
        ListModelList<User> userslist = new ListModelList<>();
        for (User user : this.users) {
            if (!user.isIsdeleted()) {
                userslist.add(user);
            }
        }
        this.users = userslist;
    }

    @Command
    public void showMessageBox() {

    }

    public void doSomething() {
        System.out.println("Method doSomething() is invoked!");
        // You can perform any logic here
    }


    public List getTodos() {
        return this.todos;
    }

    public void setTodos(List todos) {
        this.todos = todos;
    }

    public List<Todo> getAllTodos() {
        return allTodos;
    }

    public void setAllTodos(List<Todo> allTodos) {
        this.allTodos = allTodos;
    }

    public Todo getSelectedTodo() {
        return selectedTodo;
    }

    public void setSelectedTodo(Todo selectedTodo) {
        this.selectedTodo = selectedTodo;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

}
