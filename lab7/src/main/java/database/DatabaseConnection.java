package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect(String dbPath){
        try {
            connection = DriverManager.getConnection("jdbc: sqlite:"+dbPath);
            System.out.println("Connected to database");
        }catch (SQLException e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }

    }

    public void disconnect(){
        try{
            if(connection!=null&&connection.isClosed()){
                connection.close();
                System.out.println("Disconnected database");
            }
        }catch (SQLException e){
            System.out.println("Disconnection failed");
            e.printStackTrace();
        }
    }
}
