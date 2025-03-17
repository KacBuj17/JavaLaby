import java.util.Date;
import java.text.SimpleDateFormat;

public class HelloDate2 {
    public static void main(String[] args) {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println("Witaj! Teraz jest: " + formatter.format(now));
    }
}
