package org.blaisesolutions.dao;


import lombok.extern.slf4j.Slf4j;
import org.blaisesolutions.entity.Person;
import org.blaisesolutions.entity.Todo;
import org.blaisesolutions.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TodoDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Todo> queryAll() {
        Query query = em.createQuery("SELECT t FROM Todo t");
        List<Todo> result = query.getResultList();
        return result;
    }

    @Transactional(readOnly = true)
    public Todo get(Integer id) {
        return em.find(Todo.class, id);
    }

    @Transactional
    public String save(Todo todo) {
        try {
            em.persist(todo);
            return "Todo saved";
        } catch (Exception exception) {
            log.error("error at " + exception.getLocalizedMessage());
        }
        return "not saved";
    }

    @Transactional
    public void delete(Todo todo) {
        Todo t = get(Math.toIntExact(todo.getId()));
        if (t != null) {
            em.remove(t);
        }
    }

    @Transactional
    public Optional<Todo> getTodoById(Todo todo) {
        return Optional.ofNullable(em.find(Todo.class, todo.getId()));
    }

    @Transactional
    public void updateTodo(Todo todo) {
        em.merge(todo);
    }

    @Transactional
    public List<Todo> findByUser(User user) {
        try {
//            Query query = em.createQuery("SELECT t FROM Todo t WHERE :user MEMBER OF t.users"); This is used searching in a list
            Query query= em.createQuery("SELECT t FROM Todo t WHERE t.user=:user");
            query.setParameter("user", user);
            List<Todo> result = query.getResultList();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    @Transactional
    public void softDelete(Todo todo) {
        Todo r = em.find(Todo.class, todo.getId());
        if (r != null) {
            r.setComplete(true);
            em.merge(r); // merge instead of remove
        }
    }
}