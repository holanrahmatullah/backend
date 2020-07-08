package api.test.repository;

import java.util.Date;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import api.test.model.User;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

@Singleton
public class UserRepository implements UserInterface {

    @PersistenceContext
    private EntityManager manager;

    public UserRepository(@CurrentSession EntityManager entityManager) {
        this.manager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll(int page, int limit) {
        TypedQuery<User> query = manager.createQuery("from User", User.class)
                .setFirstResult(page > 1 ? page * limit - limit : 0).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    @Transactional
    public String save(@NotNull User user) {
        try {
            manager.persist(user);
            return "{\"status\":\"ok\"}";
        } catch (Exception e) {
            return "{\"status\":\"fail\", \"message\": \"" + e.getMessage() + "\"}";
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long size() {
        return manager.createQuery("select count(*) from User", Long.class).getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(@NotNull Long id) {
        return manager.find(User.class, id);
    }

    @Override
    @Transactional
    public boolean update(@NotNull Long id, String name, String password) {
        try {
            User user = manager.find(User.class, id);
            if (name != null)
                user.setUsername(name);
            if (password != null)
                user.setPassword(password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean destroy(@NotNull Long id) {
        try {
            User user = manager.find(User.class, id);
            manager.remove(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}