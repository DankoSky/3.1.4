package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            connection.createStatement()
                    .execute("CREATE TABLE IF NOT EXISTS  USER (\n" +
                            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                            "  `name` VARCHAR(45) NOT NULL,\n" +
                            "  `lastName` VARCHAR(45) NOT NULL,\n" +
                            "  `age` INT NOT NULL,\n" +
                            "  PRIMARY KEY (`id`));");
            connection.commit();
            System.out.println("Таблица успешно создана");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.printStackTrace();
            System.out.println("Ошибка создания таблицы");
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /*
        public void createUsersTable() {
            try (Connection connection = getConnection()) {

                connection.createStatement()
                        .execute("CREATE TABLE IF NOT EXISTS  USER (\n" +
                                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                                "  `name` VARCHAR(45) NOT NULL,\n" +
                                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                                "  `age` INT NOT NULL,\n" +
                                "  PRIMARY KEY (`id`));");
                System.out.println("Таблица успешно создана");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Ошибка создания таблицы");
            }
        }
    */
    public void dropUsersTable() {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            connection.createStatement()
                    .execute("DROP TABLE IF EXISTS USER");
            connection.commit();
            System.out.println("Таблица успешно удалена");
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
            System.out.println("Не удалось удалить таблицу");
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /*   public void dropUsersTable() {
           try (Connection connection = getConnection()) {

               connection.createStatement()
                       .execute("DROP TABLE IF EXISTS USER");
               System.out.println("Таблица успешно удалена");
           } catch (SQLException throwables) {
               throwables.printStackTrace();
               System.out.println("Не удалось удалить таблицу");
           }
       }
   */
    public void saveUser(String name, String lastName, byte age) {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            User user = new User(name, lastName, age);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO  user (name, lastName, age) VALUES(?, ?, ?);");

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());

            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем:" + user.getName() + " добавлен");
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Ошибка при добавлении User");
            throwables.printStackTrace();

        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /*  public void saveUser(String name, String lastName, byte age) {
          try (Connection connection = getConnection()) {

              User user = new User(name, lastName, age);
              PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO  user (name, lastName, age) VALUES(?, ?, ?);");

              preparedStatement.setString(1, user.getName());
              preparedStatement.setString(2, user.getLastName());
              preparedStatement.setByte(3, user.getAge());

              preparedStatement.executeUpdate();
              System.out.println("User с именем:" + user.getName() + " добавлен");
          } catch (SQLException throwables) {
              System.out.println("Ошибка при добавлении User");
              throwables.printStackTrace();

          }
      }*/
    public void removeUserById(long id) {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USER WHERE ID=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("Пользователь с id: " + id + " удален");

        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException throwable) {
                throwables.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /*  public void removeUserById(long id) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USER WHERE ID=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь с id: " + id + " удален");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            ResultSet resultSet = connection.createStatement()
                    .executeQuery("SELECT   id, name, lastName, age FROM user");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            connection.commit();
            System.out.println("Пользователи успешно добавлены в ArrayLIST");
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException throwabls) {
                throwables.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return userList;
    }

    /*public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = getConnection()) {
            ResultSet resultSet = connection.createStatement()
                    .executeQuery("SELECT   id, name, lastName, age FROM user");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
                System.out.println("Пользователи успешно добавлены в ArrayLIST");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }*/
    public void cleanUsersTable() {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USER");
            preparedStatement.execute();
            connection.commit();
            System.out.println("Успешно очищена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                try {
                    connection.rollback();
                } catch (SQLException thrwables) {
                    throwables.printStackTrace();
                }
                throwables.printStackTrace();
            }
        }
    }
    /* public void cleanUsersTable() {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USER");
            preparedStatement.execute();
            System.out.println("Успешно очищена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/
}
