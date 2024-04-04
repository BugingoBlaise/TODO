package org.blaisesolutions.dao;

import lombok.extern.slf4j.Slf4j;
import org.blaisesolutions.entity.Person;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Slf4j
@Repository
public class PersonDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Person> queryAll() {
        Query query = em.createQuery("SELECT p FROM Person p");
        List<Person> result = query.getResultList();
        return result;
    }

    @Transactional(readOnly = true)
    public Person get(Integer id) {
        return em.find(Person.class, id);
    }

    @Transactional
    public String save(Person person) {
        try{
            em.persist(person);
            return "person saved";
        }catch (Exception exception){
        log.error("error at "+exception.getLocalizedMessage());
        }
return "not saved";
    }

    @Transactional
    public void delete(Person person) {
        Person r = get(Math.toIntExact(person.getId()));
        if(r != null) {
            em.remove(r);
        }
    }
    @Transactional
    public void softDelete(Person person) {
        Person r = em.find(Person.class, person.getId());
        if (r != null) {
            r.setDeleted(true);
            em.merge(r); // merge instead of remove
        }
    }
}
