package server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ClientApplication {
//створити клієнта в мейні

    // Метод для відправки даних на сервер
    public void sendData(String name, String filepath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            // Читаємо файл по рядках
            while ((line = reader.readLine()) != null) {
                sendLine(name, line);// Відправляємо кожен рядок на сервер
                TimeUnit.SECONDS.sleep(2);// Пауза на 2 секунди між кожним рядком
            }
            // Після відправки усіх рядків відправляємо повідомлення про завершення
            sendTermination(name);
            reader.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для відправки одного рядка на сервер
    private void sendLine(String username, String line) throws IOException {
        String url = "http://localhost:8080/upload"; //СТВОРИТИ У СПРІНГБУТІ У ДРУГОМУ ПРОЄКТІ
        URL obj = new URL(url); //зберігає посилання у форматі посилання
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection(); //відкриває звєязок з сервером

        connection.setRequestMethod("POST");//хуй знає що воно робе але напишт
        connection.setRequestProperty("connect-type", "text-plain"); //встановлює тип даних???

        String data = username + line;
        // Відправляємо дані на сервер
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        os.close();

        // Виводимо відповідь сервера (опціонально)
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code : " + responseCode);

    }

    private void sendTermination(String username) throws IOException {
        String url = "http://localhost:8080/terminate"; // Адреса серверного endpoint
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        // Встановлюємо метод запиту та заголовки
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/plain");

        // Підготовка повідомлення про завершення
        String data = "bye," + username;

        // Відправляємо повідомлення про завершення на сервер
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        os.close();

        // Виводимо відповідь сервера (опціонально)
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code : " + responseCode);

    }

}
