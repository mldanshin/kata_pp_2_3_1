package web.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.models.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> getList() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User user", User.class)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public User getById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select user from User user where user.id=:id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void store(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update User set firstName=:firstName, lastName=:lastName where id=:id")
                    .setParameter("id", user.getId())
                    .setParameter("firstName", user.getFirstName())
                    .setParameter("lastName", user.getLastName())
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete User where id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }
}
