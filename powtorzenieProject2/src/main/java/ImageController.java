
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Controller
public class ImageController {

    // Мапінг GET-запиту на шлях "/image"
    @GetMapping("/image")
    public String getImage(@RequestParam String username, @RequestParam int electrode, Model model) {
        // Встановлення з'єднання з базою даних SQLite
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\project\\java\\egg\\src\\main\\resources\\usereeg.db")) {
            // SQL-запит для отримання зображення з бази даних за користувачем і номером електроди
            String selectDataSQL = "SELECT image FROM user_eeg WHERE username = ? AND electrode_number = ?";
            PreparedStatement statement = connection.prepareStatement(selectDataSQL);
            statement.setString(1, username);      // Встановлення параметра для користувача
            statement.setInt(2, electrode);        // Встановлення параметра для номера електроди

            // Виконання SQL-запиту і отримання результатів
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Отримання значення зображення в форматі base64 з результатів запиту
                String image = resultSet.getString("image");

                // Додавання атрибутів до моделі для передачі їх на сторінку
                model.addAttribute("username", username);
                model.addAttribute("electrode", electrode);
                model.addAttribute("image", image);

                // Повернення назви перегляду "eegimage", який буде відображати дані з моделі
                return "eegimage";
            } else {
                // Якщо запис не знайдено, можна обробити цю ситуацію або відобразити повідомлення про помилку
                return "error"; // Повернення перегляду з повідомленням про помилку
            }

        } catch (Exception e) {
            // Обробка помилок підключення до бази даних або виконання запиту
            System.err.println("Error getting image: " + e.getMessage());
            return "error"; // Повернення перегляду з повідомленням про помилку
        }
    }
}