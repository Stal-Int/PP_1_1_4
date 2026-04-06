package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        try {
            userService.createUsersTable();

            userService.saveUser("Ivan", "Ivanov", (byte) 25);
            System.out.println("User с именем — Ivan добавлен в базу данных");

            userService.saveUser("Petr", "Petrov", (byte) 30);
            System.out.println("User с именем — Petr добавлен в базу данных");

            userService.saveUser("Anna", "Sidorova", (byte) 22);
            System.out.println("User с именем — Anna добавлен в базу данных");

            userService.saveUser("Maria", "Kozlova", (byte) 28);
            System.out.println("User с именем — Maria добавлен в базу данных");

            List<User> users = userService.getAllUsers();
            for (User user : users) {
                System.out.println(user);
            }

            userService.cleanUsersTable();

            userService.dropUsersTable();

        } finally {
            Util.closeSessionFactory();
        }
    }
}