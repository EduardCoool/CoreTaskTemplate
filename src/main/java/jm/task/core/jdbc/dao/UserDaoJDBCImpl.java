package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util;

    public UserDaoJDBCImpl() {
        this.util = new Util();
    }

    public void createUsersTable() {
        try {
            util.execUpdate("create table if not exists USERS("
                    + "ID serial PRIMARY KEY NOT NULL, "
                    + "NAME VARCHAR(20) NOT NULL, "
                    + "LASTNAME VARCHAR(20) NOT NULL, "
                    + "AGE serial NOT NULL "
                    + ")");

            System.out.println("Таблица создана");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            util.execUpdate("drop table if exists  USERS");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Таблица удалена");

    }

    public void saveUser(String name, String lastName, byte age) {

        try {
            util.execUpdate("INSERT INTO USERS (NAME, LASTNAME, AGE)"
                    + "VALUES ('" + name + "','" + lastName + "','" + age + "')");
            System.out.println("name = " + name + "; lastName = " + lastName + "; age = " + age);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            util.execUpdate("DELETE FROM USERS WHERE ID=" + id);
            System.out.println("Удалён столбец по id" + id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//
    }

    public List<User> getAllUsers() {
        ResultSet resultSet;
        List<User> list = new ArrayList<>();
        try {
            resultSet = util.execQuery("SELECT * FROM USERS");

            while (resultSet.next()) {
                list.add(new User(resultSet.getString("NAME"), resultSet.getString("LASTNAME"),
                        resultSet.getByte("AGE")));
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        try {
            util.execUpdate("DELETE FROM USERS");
            System.out.println("Таблица очищена");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
