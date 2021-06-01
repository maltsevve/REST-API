package com.maltsevve.restApi.repository;

import com.maltsevve.restApi.model.User;
import com.maltsevve.restApi.util.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JavaIOUserRepositoryImpl implements UserRepository {
    public JavaIOUserRepositoryImpl() {
    }

    @Override
    public User save(User user) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return user;
    }

    @Override
    public User update(User user) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return user;
    }

    @Override
    public User getById(Long aLong) {
        User user = null;
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            user = session.get(User.class, aLong);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            users = (List<User>) session.createQuery("FROM User u LEFT JOIN FETCH u.events").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return users.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long aLong) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = session.load(User.class, aLong);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
