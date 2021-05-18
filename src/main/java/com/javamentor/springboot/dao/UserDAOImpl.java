package com.javamentor.springboot.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.javamentor.springboot.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> allUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(entityManager.merge(user));
    }

    @Override
    @Transactional
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        User managed = entityManager.merge(user);
        entityManager.remove(managed);
    }

    @Override
    public User getUserByName(String username) {
        try {
            User user = entityManager.createQuery("SELECT u FROM User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return user;
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}
