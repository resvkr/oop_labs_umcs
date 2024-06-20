package com.example.lablab10;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller// ПРОСТО контроллер
public class ImageFromController {
    @GetMapping("/")
    public String index() {
        return "index"; // Відображає файл index.html
    }

    /*Що таке MultipartFile?
      є інтерфейсом у Spring, який представляє завантажений файл, отриманий через HTTP-запит типу multipart/form-data
      Його використовують, коли потрібно обробляти файли, завантажені користувачами через веб-форму. Це може бути завантаження зображень, документів, відео тощо..*/


/* Model Використовується, коли потрібно передавати дані з контролера до HTML-сторінки, наприклад, результати обробки форми, інформацію з бази даних тощо.*/
    @PostMapping("/upload")
    public String upload(@RequestParam("image") MultipartFile imageFile, Model model) {
        try {
            // Читаємо байти зображення
            byte[] bytes = imageFile.getBytes();
            // Кодуємо байти зображення в base64
            String imageBase64 = Base64.getEncoder().encodeToString(bytes);
            // Додаємо закодоване зображення до моделі
            model.addAttribute("imageBase64", imageBase64);
        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // Повертає сторінку помилки у разі невдачі
        }
        return "image"; // Відображає файл image.html
    }

}
