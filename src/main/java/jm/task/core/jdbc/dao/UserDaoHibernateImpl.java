package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                session.createNativeQuery(
                        "CREATE TABLE IF NOT EXISTS users (" +
                                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                                "name VARCHAR(50) NOT NULL, " +
                                "lastName VARCHAR(50) NOT NULL, " +
                                "age TINYINT NOT NULL)"
                ).executeUpdate();

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Ошибка создания таблицы", e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка сессии", e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                session.createNativeQuery("DROP TABLE IF EXISTS users")
                        .executeUpdate();

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Ошибка удаления таблицы", e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка сессии", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(user);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Ошибка сохранения пользователя", e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка сессии", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                User user = session.get(User.class, id);
                if (user != null) {
                    session.delete(user);
                }

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Ошибка удаления пользователя", e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка сессии", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка получения пользователей", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createQuery("DELETE FROM User").executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Ошибка очистки таблицы", e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка сессии", e);
        }
    }
}