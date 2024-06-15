package auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {
    private final DatabaseConnection dbСonnection;

    public AccountManager(DatabaseConnection dbconnection) {
        this.dbСonnection = dbconnection;
    }

    public boolean register(String username, String password) {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        try (Connection connection = dbСonnection.getConnection()) {
            String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean authenticate(String username, String password) {
        try (Connection connection = dbСonnection.getConnection()) {
            String selectSQL = "SELECT password FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                String storedHash = resultSet.getString("password");
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), storedHash);
                return result.verified;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Account getAccount(String username) {
        try (Connection connection = dbСonnection.getConnection()) {
            String selectSQL = "SELECT id, username FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String user = resultSet.getString("username");
                return new Account(id, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccount(int id) {
        try (Connection connection = dbСonnection.getConnection()) {
            String selectSQL = "SELECT id, username FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                return new Account(id, username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
