package server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Base64;

public class ServerApplication {

    public static void main(String[] args) {
        int port = 12345; // Порт, на якому сервер буде слухати

        // Створюємо ServerSocket, який слухає на вказаному порту
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            // Сервер постійно чекає на нові з'єднання
            while (true) {
                // Приймаємо з'єднання від клієнта
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                // Для кожного клієнта створюємо новий потік для обробки
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            // Додатковий вивід помилок для кращого розуміння
            System.err.println("Could not listen on port " + port);
            e.printStackTrace();
        } catch (Exception e) {
            // Загальне оброблення виключень для інших можливих помилок
            System.err.println("An error occurred while running the server");
            e.printStackTrace();
        }
    }

    // Клас для обробки клієнтських з'єднань У ОКРЕМОМУ ПОТОЦІ
    private static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            //Цей рядок коду відкриває два потоки для з'єднання з клієнтом: один для читання вхідних даних від клієнта і один для запису вихідних даних до клієнта. Ось детальне пояснення:
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                // Отримуємо ім'я користувача
                String username = in.readLine();
                System.out.println("User" + username);

                String line;
                int electrodeNumber = 0;

                while ((line = in.readLine()) != null) {
                    if ("bye".equals(line)) {
                        System.out.println("User " + username + " finished sending data.");
                        break;
                    }

                    // Обробка отриманої лінії даних
                    String base64Graph = generateGraphBase64(line);
                    saveToDatabase(username, electrodeNumber, base64Graph);
                    electrodeNumber++;

                    // Відправляємо підтвердження клієнту
                    out.println("Data received for electrode " + electrodeNumber);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Метод для генерації графіка у форматі base64
        private String generateGraphBase64(String data) throws Exception {
            // Тут ви можете додати код для генерації графіка
            // Ми будемо використовувати BufferedImage для прикладу
            BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            // Додайте код для малювання на зображенні

            // Конвертуємо зображення у формат base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        }

        // Метод для збереження даних у базу даних
        private void saveToDatabase(String username, int electrodeNumber, String base64Graph) {
            String url = "jdbc:sqlite:eeg_data.db";

            String sql = "INSERT INTO eeg_signals(username, electrode_number, base64_graph) VALUES(?,?,?)";

            try (Connection conn = DriverManager.getConnection(url);//Відкриває з'єднання з базою даних за вказаним URL
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {//Створює підготовлений запит (PreparedStatement) з використанням SQL-рядка sql
                pstmt.setString(1, username);
                pstmt.setInt(2, electrodeNumber);
                pstmt.setString(3, base64Graph);
                pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

