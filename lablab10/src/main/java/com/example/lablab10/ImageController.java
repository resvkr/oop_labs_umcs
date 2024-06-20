package com.example.lablab10;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController// якщо скажуть додати рест контроллер
public class ImageController {
    @GetMapping("/adjustBrightness")
    public String adjustBrightness(@RequestParam String imageBase64, @RequestParam int brightness) throws IOException {
// Декодування зображення base64
        byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage image = ImageIO.read(bis);

        // Зміна яскравості зображення
        BufferedImage brightenedImage = changeBrightness(image, brightness);

        // Кодування зображення назад у base64
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(brightenedImage, "png", bos);
        byte[] brightenedImageBytes = bos.toByteArray();
        return Base64.getEncoder().encodeToString(brightenedImageBytes);
    }

    private BufferedImage changeBrightness(BufferedImage image, int brightness) {
        // Створюємо нове зображення з такими ж розмірами та типом, як оригінальне зображення
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        // Проходимо через кожен піксель зображення
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                // Отримуємо RGBA значення для поточного пікселя
                int rgba = image.getRGB(x, y);

                // Створюємо об'єкт Color з поточних RGBA значень
                Color col = new Color(rgba, true);

                // Збільшуємо значення кожного з компонентів кольору (червоний, зелений, синій) на значення яскравості
                int red = col.getRed() + brightness;
                int green = col.getGreen() + brightness;
                int blue = col.getBlue() + brightness;

                // Гарантуємо, що значення компонентів кольору знаходяться в межах від 0 до 255
                red = Math.max(0, Math.min(255, red));
                green = Math.max(0, Math.min(255, green));
                blue = Math.max(0, Math.min(255, blue));

                // Створюємо новий колір з модифікованими значеннями
                col = new Color(red, green, blue, col.getAlpha());

                // Встановлюємо новий колір для поточного пікселя в результуючому зображенні
                result.setRGB(x, y, col.getRGB());
            }
        }

        // Повертаємо результуюче зображення з модифікованою яскравістю
        return result;
    }

    private BufferedImage changeBrightnessToBufferedImage(BufferedImage image, int brightness) {
        // Створюємо нове зображення з такими ж розмірами та типом, як оригінальне зображення
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        // Проходимо через кожен піксель зображення
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                // Отримуємо RGBA значення для поточного пікселя
                int rgba = image.getRGB(x, y);

                // Створюємо об'єкт Color з поточних RGBA значень
                Color col = new Color(rgba, true);

                // Збільшуємо значення кожного з компонентів кольору (червоний, зелений, синій) на значення яскравості
                int red = col.getRed() + brightness;
                int green = col.getGreen() + brightness;
                int blue = col.getBlue() + brightness;

                // Гарантуємо, що значення компонентів кольору знаходяться в межах від 0 до 255
                red = Math.max(0, Math.min(255, red));
                green = Math.max(0, Math.min(255, green));
                blue = Math.max(0, Math.min(255, blue));

                // Створюємо новий колір з модифікованими значеннями
                col = new Color(red, green, blue, col.getAlpha());

                // Встановлюємо новий колір для поточного пікселя в результуючому зображенні
                result.setRGB(x, y, col.getRGB());
            }
        }

        // Повертаємо результуюче зображення з модифікованою яскравістю
        return result;
    }
}

