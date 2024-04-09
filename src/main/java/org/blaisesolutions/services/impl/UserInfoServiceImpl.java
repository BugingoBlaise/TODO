
package org.blaisesolutions.services.impl;


import org.blaisesolutions.dao.UserDao;
import org.blaisesolutions.entity.Todo;
import org.blaisesolutions.entity.User;
import org.blaisesolutions.services.TodoListService;
import org.blaisesolutions.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service("userInfoService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserInfoServiceImpl implements UserInfoService, Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    UserDao userDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TodoListService todoListService;

    @Override
    public User findUser(String account) {
        Optional<User> userOptional = userDao.findUserByEmail(account);
        return userOptional.orElse(null);
    }

    @Override
    public String updateUser(User user) {
        Optional<User> existingUser = userDao.getUserById(user);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User not found");
        } else {

            userDao.update(existingUser.get());
            return "Assigned Successfully";
        }
    }


    @Override
    public String create(User user) {
        // Check if user already exists
        User existingUser = findUser(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("User already exists");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userDao.save(user);
        }
    }

    @Override
    public List<User> findAll() {
        return userDao.queryAll();
    }

    @Override
    public List<User> searchUserByName(String name) {
        List<User> allUsers = findAll();
        List<User> result = new LinkedList<>();

        if (name == null || name.isEmpty()) {
            result.addAll(allUsers); // Add all users if the search name is empty
        } else {
            // Iterate through all users and check if the name matches
            for (User user : allUsers) {
                if (
                        user.getFullName().toLowerCase().contains(name.toLowerCase())
                                || user.getEmail().toLowerCase().contains(name.toLowerCase())) {
                    result.add(user);
                }
            }
        }
        return result;

    }

    @Override
    public void deleteUser(User selectedUser) {
        try {


        // Retrieve todos associated with the user
        List<Todo> todosToDelete = todoListService.findByUser(selectedUser);

        // Remove references to the user in todos
        for (Todo todo : todosToDelete) {
           String u= todo.getUser().getFullName();
            System.out.println(u);
            todo.setUser(null); // Or update with a different user if needed
        }

        // Delete the user
        userDao.delete(selectedUser);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public String softDelete(User user) {
        try{
            userDao.softDelete(user);
            return "Deleted successfully";
        }catch (Exception ex){
//            log.error("Error deleting todo: {}", ex.getMessage());
            return "Failed to delete todo: " + ex.getMessage();
        }
    }

}
