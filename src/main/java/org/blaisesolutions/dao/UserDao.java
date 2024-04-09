package org.blaisesolutions.dao;


import lombok.extern.slf4j.Slf4j;
import org.blaisesolutions.entity.Todo;
import org.blaisesolutions.entity.User;
import org.blaisesolutions.entity.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserDao  {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<User> queryAll() {
        Query query = em.createQuery("SELECT u FROM User u");
        List<User> result = query.getResultList();
        return result;
    }
    public Optional<User> findUserByEmail(String email) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
        query.setParameter("email", email);

        // Execute the query and retrieve the result
        List<User> resultList = query.getResultList();

        // If the result list is empty, return Optional.empty()
        if (resultList.isEmpty()) {
            return Optional.empty();
        }

        // If the result list contains a user, return it wrapped in an Optional
        return Optional.of(resultList.get(0));
    }



    @Transactional(readOnly = true)
    public User findById(Integer id) {
        return em.find(User.class,id);
    }

    @Transactional
    public String save(User user) {
        try{
            em.persist(user);
            return "user saved";
        }catch (Exception exception){
            log.error("error at "+exception.getLocalizedMessage());
        }
        return "not saved";
    }
    @Transactional
    public void update(User user) {
        try{
             em.merge(user);
         }catch (Exception exception){
            log.error("error at "+exception.getLocalizedMessage());
        }
     }


    @Transactional
    public boolean authenticate(String email, String password) {
        try {
             Query querry=em.createQuery("SELECT u FROM User u WHERE u.email=:email AND u.password=:password");
            querry.setParameter("email", email);
            querry.setParameter("password", password);

            User user = (User) querry.getResultList();
            return user != null; // Return true if user object is not null
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false; // Return false for any exception
    }
    @Transactional
    public List<User> searchUserByName(String keyword) {
        return em.createNamedQuery("User.searchUserByName", User.class)
                .setParameter("kw", "%" + keyword + "%") // Use wildcard (%) to match any part of the name
                .getResultList();
    }
    @Transactional
    public Optional<User> getUserById(User user) {
        return Optional.ofNullable(em.find(User.class, user.getId()));
    }
    @Transactional
    public void delete(User user) {
        User t = get(Math.toIntExact(user.getId()));
        if (t != null) {
            em.remove(t);
        }
    }
    @Transactional(readOnly = true)
    public User get(Integer id) {
        return em.find(User.class, id);
    }
    @Transactional
    public void softDelete(User user) {
        User r = em.find(User.class, user.getId());
        if (r != null) {
            r.setIsdeleted(true);
            em.merge(r); // merge instead of remove
        }
    }
}
