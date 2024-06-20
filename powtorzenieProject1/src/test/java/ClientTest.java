import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test.csv", numLinesToSkip = 1)
    void testSendData(String username, String csvFilePath, String expectedBase64Image) throws IOException {
        // Відправляємо дані на сервер за допомогою методу sendData
        sendData(username, csvFilePath);

        // Отримуємо з сервера реальний результат у форматі base64 (потрібно реалізувати метод)
        String receivedBase64Image = receiveImageFromServer();

        // Перевіряємо, чи отримали очікуваний результат
        assertEquals(expectedBase64Image, receivedBase64Image, "Отримане зображення не відповідає очікуваному");
    }

    // Метод, що симулює відправку даних на сервер
    private void sendData(String username, String csvFilePath) {
        System.out.println("Відправлення даних на сервер для користувача " + username + " з файлу " + csvFilePath);
        // Тут можна реалізувати відправку даних на сервер
    }

    // Метод, що симулює отримання зображення з сервера
    private String receiveImageFromServer() {
        System.out.println("Отримання зображення з сервера");
        // Тут можна реалізувати отримання зображення з сервера і повернення його у форматі base64
        return "mockBase64Image"; // Заглушка для прикладу
    }
}