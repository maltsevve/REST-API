package com.maltsevve.restApi.repository;

import com.maltsevve.restApi.model.User;
import com.maltsevve.restApi.util.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jpa.QueryHints;

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
            session = HibernateSessionFactory.getSession();
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
            session = HibernateSessionFactory.getSession();
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

        try {
            session = HibernateSessionFactory.getSession();
            user = session.get(User.class, aLong);
        } catch (Exception e) {
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

        try {
            session = HibernateSessionFactory.getSession();
            users = (List<User>) session.createQuery("SELECT DISTINCT user FROM User user LEFT JOIN FETCH user.events").
            setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
        } catch (Exception e) {
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
            session = HibernateSessionFactory.getSession();
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
