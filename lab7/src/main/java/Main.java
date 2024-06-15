import auth.Account;
import auth.AccountManager;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String dbPath = "test.db";

        dbConnection.connect(dbPath);

        try (Connection connection = dbConnection.getConnection()) {
            if (connection != null) {
                Statement statement = connection.createStatement();
                // Create table
                String createTableSQL = "CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, name TEXT NOT NULL)";
                statement.execute(createTableSQL);
                // Insert data
                String insertSQL = "INSERT INTO users (name) VALUES ('John Doe')";
                statement.execute(insertSQL);

                // Read data
                String selectSQL = "SELECT * FROM users";
                ResultSet resultSet = statement.executeQuery(selectSQL);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    System.out.println("User ID: \" + id + \", Name: \" + name");
                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.disconnect();
        }


        try (Connection connection = dbConnection.getConnection()) {
            if (connection != null) {
                Statement statement = connection.createStatement();

                // Create table with username and password
                String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT NOT NULL UNIQUE, password TEXT NOT NULL)";
                statement.execute(createTableSQL);

                AccountManager accountManager = new AccountManager(dbConnection);

                // Register a new user
                String username = "JohnDoe";
                String password = "password123";
                if (accountManager.register(username, password)) {
                    System.out.println("User registered successfully.");
                } else {
                    System.out.println("User registration failed.");
                }

                // Authenticate the user
                if (accountManager.authenticate(username, password)) {
                    System.out.println("User authenticated successfully.");
                } else {
                    System.out.println("User authentication failed.");
                }

                // Get account by username
                Account account = accountManager.getAccount(username);
                if (account != null) {
                    System.out.println("Account found: " + account);
                } else {
                    System.out.println("Account not found.");
                }

                // Get account by id
                if (account != null) {
                    Account accountById = accountManager.getAccount(account.id());
                    if (accountById != null) {
                        System.out.println("Account found by ID: " + accountById);
                    } else {
                        System.out.println("Account not found by ID.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.disconnect();
        }
    }
}

