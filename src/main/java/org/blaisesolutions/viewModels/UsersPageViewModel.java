package org.blaisesolutions.viewModels;

import lombok.Getter;
import lombok.Setter;
import org.blaisesolutions.entity.Todo;
import org.blaisesolutions.entity.User;
import org.blaisesolutions.entity.UserCredential;
import org.blaisesolutions.services.TodoListService;
import org.blaisesolutions.services.UserInfoService;
import org.zkoss.bind.annotation.ExecutionParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UsersPageViewModel {

    @WireVariable
    private UserInfoService userInfoService;
    @WireVariable
    private TodoListService todoListService;
    private User user;
    private List<Todo> userTodos = new ArrayList<>();

    @Init
    public void init() {
        // Retrieve the logged-in user information from the session
        Session session = Sessions.getCurrent();
        User userInfo = (User) session.getAttribute("userInfo");
        if (userInfo != null) {
            // Retrieve user information using UserInfoService
            this.user = userInfoService.findUser(userInfo.getEmail());
            // Load all tasks associated with the user
            this.userTodos = todoListService.findByUser(user);
        }
    }
}
