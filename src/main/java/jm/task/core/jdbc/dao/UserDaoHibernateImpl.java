package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final Session session = Util.getPostgreConfiguration().openSession();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("create table if not exists USERS("
                + "ID serial PRIMARY KEY NOT NULL, "
                + "NAME VARCHAR(20) NOT NULL, "
                + "LASTNAME VARCHAR(20) NOT NULL, "
                + "AGE serial NOT NULL "
                + ")").executeUpdate();
        transaction.commit();
    }


    @Override
    public void dropUsersTable() {
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("drop table if exists Users").executeUpdate();
        transaction.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(User.class, id));
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        return session.createQuery("SELECT a FROM User a", User.class).getResultList();
    }


    @Override
    public void cleanUsersTable() {
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DELETE FROM USERS").executeUpdate();
        transaction.commit();
    }
}
