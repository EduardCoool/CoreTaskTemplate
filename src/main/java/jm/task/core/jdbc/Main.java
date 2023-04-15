package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl(new UserDaoJDBCImpl());

        userService.createUsersTable();
        userService.saveUser("ED", "prokurin", (byte) 25);
        userService.saveUser("ED2", "prokurin", (byte) 26);
        userService.saveUser("ED3", "prokurin", (byte) 27);
        userService.saveUser("ED4", "prokurin", (byte) 28);
        userService.removeUserById(1);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
//        userService.cleanUsersTable();
//        userService.dropUsersTable();
    }
}
