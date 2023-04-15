package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        try {
            this.connection = Util.getPostgreConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {

        try (Statement stmt = connection.createStatement()) {

            stmt.executeUpdate("create table if not exists USERS("
                    + "ID serial PRIMARY KEY NOT NULL, "
                    + "NAME VARCHAR(20) NOT NULL, "
                    + "LASTNAME VARCHAR(20) NOT NULL, "
                    + "AGE serial NOT NULL "
                    + ")");
            stmt.close();
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("drop table if exists  USERS");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Таблица удалена");
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO USERS (NAME, LASTNAME, AGE) " +
                "Values (?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
            System.out.println("name = " + name + "; lastName = " + lastName + "; age = " + age);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void removeUserById(long id) {
        try (CallableStatement cstmt = connection.prepareCall(" call deleteuserbyid(?) ")) {
            cstmt.setInt(1, (int) id);
            cstmt.executeUpdate();
            System.out.println("Удалён столбец по id" + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> getAllUsers() {


        ResultSet resultSet;
        List<User> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            resultSet = stmt.executeQuery("SELECT * FROM USERS");
            while (resultSet.next()) {
                list.add(new User(resultSet.getString("NAME"), resultSet.getString("LASTNAME"),
                        resultSet.getByte("AGE")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM USERS");
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
