import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UploadController {
    @PostMapping("/upload")

    public String handleUpload(@RequestBody String data){
        String[] parts = data.split(",");
        String username = parts[0];
        String csvLine = parts[1];

        // Обробка отриманої інформації, наприклад, збереження до бази даних чи інше
        System.out.println("Received data from user: " + username);
        System.out.println("CSV line: " + csvLine);

        // Відповідь клієнту, що дані отримані успішно
        return "Data received successfully!";


    }
@PostMapping("/terminate")
    public String handleTermination(@RequestBody String data){

    // Розділити дані про завершення на "bye" та ім'я користувача
    String[] parts = data.split(",");
    String command = parts[0];
    String username = parts[1];

    //// Обробка отриманої команди про завершення
    if ("bye".equals(command)) {
        System.out.println("User " + username + " terminated the upload.");
    }

    return "Termination command received";

}

}
