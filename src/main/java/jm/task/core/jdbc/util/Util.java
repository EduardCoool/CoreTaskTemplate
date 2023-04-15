package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД

    public static Connection getPostgreConnection() throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        String URL = "jdbc:postgresql://localhost:5432/DBsql";
        String USER = "postgres";
        String PASS = "2516";

        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static SessionFactory getPostgreConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/DBsql");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "2516");
        configuration.setProperty("hibernate.show_sql", "true");

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }


}
