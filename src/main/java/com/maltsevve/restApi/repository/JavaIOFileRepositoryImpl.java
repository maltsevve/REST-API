package com.maltsevve.restApi.repository;

import com.maltsevve.restApi.model.File;
import com.maltsevve.restApi.util.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JavaIOFileRepositoryImpl implements FileRepository {
    public JavaIOFileRepositoryImpl() {
    }

    @Override
    public File save(File file) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateSessionFactory.getSession();
            transaction = session.beginTransaction();
            session.save(file);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return file;
    }

    @Override
    public File update(File file) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateSessionFactory.getSession();
            transaction = session.beginTransaction();
            session.update(file);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return file;
    }

    @Override
    public File getById(Long aLong) {
        File file = null;
        Session session = null;

        try {
            session = HibernateSessionFactory.getSession();
            file = session.get(File.class, aLong);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return file;
    }

    @Override
    public List<File> getAll() {
        List<File> files = new ArrayList<>();
        Session session = null;

        try {
            session = HibernateSessionFactory.getSession();
            files = (List<File>) session.createQuery("FROM File f").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return files.stream().sorted(Comparator.comparing(File::getId)).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long aLong) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateSessionFactory.getSession();
            transaction = session.beginTransaction();
            File file = session.load(File.class, aLong);
            session.delete(file);
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
