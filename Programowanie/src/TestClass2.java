import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestClass2 {
    public static void main(String[] args) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println("Witaj! Teraz jest: " + formattedDateTime);
    }
}
